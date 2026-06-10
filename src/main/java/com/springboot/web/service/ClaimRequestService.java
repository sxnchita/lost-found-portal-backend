package com.springboot.web.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.web.dto.ClaimRequestRequestDto;
import com.springboot.web.dto.ClaimRequestResponseDto;
import com.springboot.web.entity.ClaimRequest;
import com.springboot.web.entity.ItemMatch;
import com.springboot.web.entity.User;
import com.springboot.web.exception.ResourceNotFoundException;
import com.springboot.web.repository.ClaimRequestRepository;
import com.springboot.web.repository.ItemMatchRepository;
import com.springboot.web.repository.UserRepository;

@Service
public class ClaimRequestService {

    @Autowired
    private ClaimRequestRepository claimRequestRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private ItemMatchRepository itemMatchRepository;

    @Autowired
    private UserRepository userRepository;

    public ClaimRequestResponseDto createClaimRequest(ClaimRequestRequestDto dto) {

        ItemMatch itemMatch = itemMatchRepository.findById(dto.getMatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Match Not Found"));

        User claimant = userRepository.findById(dto.getClaimantId())
                .orElseThrow(() -> new ResourceNotFoundException("Claimant User Not Found"));

        ClaimRequest claimRequest = new ClaimRequest();

        claimRequest.setItemMatch(itemMatch);
        claimRequest.setClaimant(claimant);
        claimRequest.setOwnershipProof(dto.getOwnershipProof());
        claimRequest.setSpecialMarks(dto.getSpecialMarks());
        claimRequest.setAdditionalNotes(dto.getAdditionalNotes());

        ClaimRequest savedClaim = claimRequestRepository.save(claimRequest);

        auditLogService.createAuditLog(
                claimant.getUserId(),
                "CREATED_CLAIM_REQUEST",
                "ClaimRequest",
                savedClaim.getClaimId()
        );

        return convertToResponseDto(savedClaim);
    }

    public List<ClaimRequestResponseDto> getAllClaims() {
        return claimRequestRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public ClaimRequestResponseDto approveClaim(Long claimId) {

        ClaimRequest claimRequest = claimRequestRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim Request Not Found"));

        claimRequest.setClaimStatus("APPROVED");
        claimRequest.setReviewedAt(LocalDateTime.now());

        ClaimRequest updatedClaim = claimRequestRepository.save(claimRequest);

        if (updatedClaim.getClaimant() != null) {

            notificationService.createNotification(
                    updatedClaim.getClaimant().getUserId(),
                    "Claim Approved",
                    "Your claim request has been approved. Please wait for handover scheduling.",
                    "CLAIM_APPROVED"
            );

            auditLogService.createAuditLog(
                    updatedClaim.getClaimant().getUserId(),
                    "APPROVED_CLAIM_REQUEST",
                    "ClaimRequest",
                    updatedClaim.getClaimId()
            );
        }

        return convertToResponseDto(updatedClaim);
    }

    public ClaimRequestResponseDto rejectClaim(Long claimId) {

        ClaimRequest claimRequest = claimRequestRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim Request Not Found"));

        claimRequest.setClaimStatus("REJECTED");
        claimRequest.setReviewedAt(LocalDateTime.now());

        ClaimRequest updatedClaim = claimRequestRepository.save(claimRequest);

        if (updatedClaim.getClaimant() != null) {

            notificationService.createNotification(
                    updatedClaim.getClaimant().getUserId(),
                    "Claim Rejected",
                    "Your claim request has been rejected after review.",
                    "CLAIM_REJECTED"
            );

            auditLogService.createAuditLog(
                    updatedClaim.getClaimant().getUserId(),
                    "REJECTED_CLAIM_REQUEST",
                    "ClaimRequest",
                    updatedClaim.getClaimId()
            );
        }

        return convertToResponseDto(updatedClaim);
    }

    private ClaimRequestResponseDto convertToResponseDto(ClaimRequest claimRequest) {

        ClaimRequestResponseDto dto = new ClaimRequestResponseDto();

        dto.setClaimId(claimRequest.getClaimId());
        dto.setOwnershipProof(claimRequest.getOwnershipProof());
        dto.setSpecialMarks(claimRequest.getSpecialMarks());
        dto.setAdditionalNotes(claimRequest.getAdditionalNotes());
        dto.setClaimStatus(claimRequest.getClaimStatus());
        dto.setReviewedAt(claimRequest.getReviewedAt());
        dto.setCreatedAt(claimRequest.getCreatedAt());

        if (claimRequest.getItemMatch() != null) {
            dto.setMatchId(claimRequest.getItemMatch().getMatchId());
        }

        if (claimRequest.getClaimant() != null) {
            dto.setClaimantId(claimRequest.getClaimant().getUserId());
            dto.setClaimantName(claimRequest.getClaimant().getFullName());
        }

        return dto;
    }
}