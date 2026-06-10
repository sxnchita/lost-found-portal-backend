package com.springboot.web.dto;

import jakarta.validation.constraints.NotBlank;

public class ClaimRequestRequestDto {

    private Long matchId;

    private Long claimantId;

    @NotBlank(message = "Ownership proof is required")
    private String ownershipProof;

    private String specialMarks;

    private String additionalNotes;

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Long getClaimantId() {
        return claimantId;
    }

    public void setClaimantId(Long claimantId) {
        this.claimantId = claimantId;
    }

    public String getOwnershipProof() {
        return ownershipProof;
    }

    public void setOwnershipProof(String ownershipProof) {
        this.ownershipProof = ownershipProof;
    }

    public String getSpecialMarks() {
        return specialMarks;
    }

    public void setSpecialMarks(String specialMarks) {
        this.specialMarks = specialMarks;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }
}
