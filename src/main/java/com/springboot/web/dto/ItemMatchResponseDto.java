package com.springboot.web.dto;

import java.time.LocalDateTime;

public class ItemMatchResponseDto {
    private Long matchId;

    private Long lostItemId;
    private String lostItemName;
    private Long lostReportedById;
    private String lostReportedByName;

    private Long foundItemId;
    private String foundItemName;
    private Long foundReportedById;
    private String foundReportedByName;

    private Integer matchScore;
    private String matchLevel;
    private String matchStatus;
    private LocalDateTime createdAt;

    public Long getMatchId() { return matchId; }
    public void setMatchId(Long matchId) { this.matchId = matchId; }

    public Long getLostItemId() { return lostItemId; }
    public void setLostItemId(Long lostItemId) { this.lostItemId = lostItemId; }

    public String getLostItemName() { return lostItemName; }
    public void setLostItemName(String lostItemName) { this.lostItemName = lostItemName; }

    public Long getLostReportedById() { return lostReportedById; }
    public void setLostReportedById(Long lostReportedById) { this.lostReportedById = lostReportedById; }

    public String getLostReportedByName() { return lostReportedByName; }
    public void setLostReportedByName(String lostReportedByName) { this.lostReportedByName = lostReportedByName; }

    public Long getFoundItemId() { return foundItemId; }
    public void setFoundItemId(Long foundItemId) { this.foundItemId = foundItemId; }

    public String getFoundItemName() { return foundItemName; }
    public void setFoundItemName(String foundItemName) { this.foundItemName = foundItemName; }

    public Long getFoundReportedById() { return foundReportedById; }
    public void setFoundReportedById(Long foundReportedById) { this.foundReportedById = foundReportedById; }

    public String getFoundReportedByName() { return foundReportedByName; }
    public void setFoundReportedByName(String foundReportedByName) { this.foundReportedByName = foundReportedByName; }

    public Integer getMatchScore() { return matchScore; }
    public void setMatchScore(Integer matchScore) { this.matchScore = matchScore; }

    public String getMatchLevel() { return matchLevel; }
    public void setMatchLevel(String matchLevel) { this.matchLevel = matchLevel; }

    public String getMatchStatus() { return matchStatus; }
    public void setMatchStatus(String matchStatus) { this.matchStatus = matchStatus; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}