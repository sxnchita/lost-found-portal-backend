package com.springboot.web.dto;

import java.time.LocalDateTime;

public class ItemMatchResponseDto {
    private Long matchId;
    private Long lostItemId;
    private String lostItemName;
    private Long foundItemId;
    private String foundItemName;
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

    public Long getFoundItemId() { return foundItemId; }
    public void setFoundItemId(Long foundItemId) { this.foundItemId = foundItemId; }

    public String getFoundItemName() { return foundItemName; }
    public void setFoundItemName(String foundItemName) { this.foundItemName = foundItemName; }

    public Integer getMatchScore() { return matchScore; }
    public void setMatchScore(Integer matchScore) { this.matchScore = matchScore; }

    public String getMatchLevel() { return matchLevel; }
    public void setMatchLevel(String matchLevel) { this.matchLevel = matchLevel; }

    public String getMatchStatus() { return matchStatus; }
    public void setMatchStatus(String matchStatus) { this.matchStatus = matchStatus; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}