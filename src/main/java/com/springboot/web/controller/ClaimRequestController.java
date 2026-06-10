package com.springboot.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.springboot.web.dto.ClaimRequestRequestDto;
import com.springboot.web.dto.ClaimRequestResponseDto;
import com.springboot.web.service.ClaimRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/claims")
public class ClaimRequestController {

    @Autowired
    private ClaimRequestService claimRequestService;

    @PostMapping
    public ClaimRequestResponseDto createClaimRequest(
            @Valid @RequestBody ClaimRequestRequestDto claimRequestRequestDto) {

        return claimRequestService.createClaimRequest(claimRequestRequestDto);
    }

    @GetMapping
    public List<ClaimRequestResponseDto> getAllClaims() {
        return claimRequestService.getAllClaims();
    }

    @PutMapping("/{id}/approve")
    public ClaimRequestResponseDto approveClaim(@PathVariable Long id) {
        return claimRequestService.approveClaim(id);
    }

    @PutMapping("/{id}/reject")
    public ClaimRequestResponseDto rejectClaim(@PathVariable Long id) {
        return claimRequestService.rejectClaim(id);
    }
}
