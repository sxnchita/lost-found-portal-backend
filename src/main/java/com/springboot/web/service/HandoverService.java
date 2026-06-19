package com.springboot.web.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.web.dto.HandoverRequestDto;
import com.springboot.web.dto.HandoverResponseDto;
import com.springboot.web.entity.ClaimRequest;
import com.springboot.web.entity.HandoverSchedule;
import com.springboot.web.entity.User;
import com.springboot.web.exception.ResourceNotFoundException;
import com.springboot.web.repository.ClaimRequestRepository;
import com.springboot.web.repository.HandoverScheduleRepository;
import com.springboot.web.repository.UserRepository;

@Service
public class HandoverService {

    @Autowired
    private HandoverScheduleRepository handoverScheduleRepository;

    @Autowired
    private ClaimRequestRepository claimRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuditLogService auditLogService;

    public HandoverResponseDto scheduleHandover(HandoverRequestDto dto) {

        ClaimRequest claimRequest = claimRequestRepository.findById(dto.getClaimId())
                .orElseThrow(() -> new ResourceNotFoundException("Claim Request Not Found"));

        if (handoverScheduleRepository.findByClaimRequest_ClaimId(dto.getClaimId()).isPresent()) {
            throw new RuntimeException("Handover already scheduled for this claim");
        }

        User scheduledBy = userRepository.findById(dto.getScheduledById())
                .orElseThrow(() -> new ResourceNotFoundException("Scheduler User Not Found"));

        HandoverSchedule handover = new HandoverSchedule();

        handover.setClaimRequest(claimRequest);
        handover.setScheduledBy(scheduledBy);
        handover.setPickupLocation(dto.getPickupLocation());
        handover.setPickupDate(dto.getPickupDate());
        handover.setPickupTime(dto.getPickupTime());
        handover.setInstructions(dto.getInstructions());
        handover.setHandoverStatus("SCHEDULED");

        HandoverSchedule savedHandover = handoverScheduleRepository.save(handover);

        if (claimRequest.getClaimant() != null) {
            notificationService.createNotification(
                    claimRequest.getClaimant().getUserId(),
                    "Handover Scheduled",
                    "Your item handover has been scheduled. Please check pickup details.",
                    "HANDOVER_SCHEDULED"
            );
        }

        auditLogService.createAuditLog(
                scheduledBy.getUserId(),
                "SCHEDULED_HANDOVER",
                "HandoverSchedule",
                savedHandover.getHandoverId()
        );

        return convertToResponseDto(savedHandover);
    }

    public List<HandoverResponseDto> getAllHandovers() {
        return handoverScheduleRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<HandoverResponseDto> getHandoversByStudent(Long userId) {
        return handoverScheduleRepository.findByClaimRequest_Claimant_UserId(userId)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public HandoverResponseDto markHandoverCompleted(Long handoverId) {

        HandoverSchedule handover = handoverScheduleRepository.findById(handoverId)
                .orElseThrow(() -> new ResourceNotFoundException("Handover Schedule Not Found"));

        handover.setHandoverStatus("COMPLETED");

        HandoverSchedule updatedHandover = handoverScheduleRepository.save(handover);

        if (
                handover.getClaimRequest() != null &&
                handover.getClaimRequest().getClaimant() != null
        ) {
            notificationService.createNotification(
                    handover.getClaimRequest().getClaimant().getUserId(),
                    "Handover Completed",
                    "Your item handover has been marked as completed.",
                    "HANDOVER_COMPLETED"
            );
        }

        auditLogService.createAuditLog(
                handover.getScheduledBy() != null ? handover.getScheduledBy().getUserId() : null,
                "COMPLETED_HANDOVER",
                "HandoverSchedule",
                updatedHandover.getHandoverId()
        );

        return convertToResponseDto(updatedHandover);
    }

    private HandoverResponseDto convertToResponseDto(HandoverSchedule handover) {

        HandoverResponseDto dto = new HandoverResponseDto();

        dto.setHandoverId(handover.getHandoverId());
        dto.setPickupLocation(handover.getPickupLocation());
        dto.setPickupDate(handover.getPickupDate());
        dto.setPickupTime(handover.getPickupTime());
        dto.setInstructions(handover.getInstructions());
        dto.setHandoverStatus(handover.getHandoverStatus());
        dto.setCreatedAt(handover.getCreatedAt());

        if (handover.getClaimRequest() != null) {
            dto.setClaimId(handover.getClaimRequest().getClaimId());
        }

        if (handover.getScheduledBy() != null) {
            dto.setScheduledById(handover.getScheduledBy().getUserId());
        }

        return dto;
    }
}