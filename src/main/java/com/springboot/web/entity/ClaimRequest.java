package com.springboot.web.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "claim_requests")
public class ClaimRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private ItemMatch itemMatch;

    @ManyToOne
    @JoinColumn(name = "claimant_id")
    private User claimant;

    @NotBlank(message = "Ownership proof is required")
    @Column(length = 1000)
    private String ownershipProof;

    @Column(length = 1000)
    private String specialMarks;

    @Column(length = 1000)
    private String additionalNotes;

    private String claimStatus;
    // PENDING, UNDER_REVIEW, APPROVED, REJECTED, COMPLETED

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    private LocalDateTime reviewedAt;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();

        if (claimStatus == null) {
            claimStatus = "PENDING";
        }
    }

    public ClaimRequest() {
    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public ItemMatch getItemMatch() {
        return itemMatch;
    }

    public void setItemMatch(ItemMatch itemMatch) {
        this.itemMatch = itemMatch;
    }

    public User getClaimant() {
        return claimant;
    }

    public void setClaimant(User claimant) {
        this.claimant = claimant;
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

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public User getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(User reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
