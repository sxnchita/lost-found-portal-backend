package com.springboot.web.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class HandoverRequestDto {

    private Long claimId;

    private Long scheduledById;

    private String pickupLocation;

    private LocalDate pickupDate;

    private LocalTime pickupTime;

    private String instructions;

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public Long getScheduledById() {
        return scheduledById;
    }

    public void setScheduledById(Long scheduledById) {
        this.scheduledById = scheduledById;
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
}