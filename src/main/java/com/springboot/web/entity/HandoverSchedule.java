package com.springboot.web.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "handover_schedules")
public class HandoverSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long handoverId;

    @OneToOne
    @JoinColumn(name = "claim_id")
    private ClaimRequest claimRequest;

    @ManyToOne
    @JoinColumn(name = "scheduled_by")
    private User scheduledBy;

    private String pickupLocation;

    private LocalDate pickupDate;

    private LocalTime pickupTime;

    @Column(length = 1000)
    private String instructions;

    private String handoverStatus;
    // SCHEDULED, COMPLETED, CANCELLED

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();

        if (handoverStatus == null) {
            handoverStatus = "SCHEDULED";
        }
    }

    public HandoverSchedule() {
    }

    public Long getHandoverId() {
        return handoverId;
    }

    public void setHandoverId(Long handoverId) {
        this.handoverId = handoverId;
    }

    public ClaimRequest getClaimRequest() {
        return claimRequest;
    }

    public void setClaimRequest(ClaimRequest claimRequest) {
        this.claimRequest = claimRequest;
    }

    public User getScheduledBy() {
        return scheduledBy;
    }

    public void setScheduledBy(User scheduledBy) {
        this.scheduledBy = scheduledBy;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getHandoverStatus() {
        return handoverStatus;
    }

    public void setHandoverStatus(String handoverStatus) {
        this.handoverStatus = handoverStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
