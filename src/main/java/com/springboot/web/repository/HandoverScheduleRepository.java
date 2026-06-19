package com.springboot.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.web.entity.HandoverSchedule;

public interface HandoverScheduleRepository extends JpaRepository<HandoverSchedule, Long> {

    // Find all handovers for a specific student (claimant)
    List<HandoverSchedule> findByClaimRequest_Claimant_UserId(Long userId);

    // Prevent duplicate handovers for the same claim
    Optional<HandoverSchedule> findByClaimRequest_ClaimId(Long claimId);
}