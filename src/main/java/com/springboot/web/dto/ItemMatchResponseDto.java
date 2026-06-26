package com.springboot.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ItemMatchResponseDto {
    private Long matchId;

    private Long lostItemId;
    private String lostItemName;
    private String lostCategory;
    private String lostColor;
    private String lostLocation;
    private LocalDate lostDate;
    private String lostDescription;
    private Long lostReportedById;
    private String lostReportedByName;

    private Long foundItemId;
    private String foundItemName;
    private String foundCategory;
    private String foundColor;
    private String foundLocation;
    private LocalDate foundDate;
    private String foundDescription;
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

    public String getLostCategory() { return lostCategory; }
    public void setLostCategory(String lostCategory) { this.lostCategory = lostCategory; }

    public String getLostColor() { return lostColor; }
    public void setLostColor(String lostColor) { this.lostColor = lostColor; }

    public String getLostLocation() { return lostLocation; }
    public void setLostLocation(String lostLocation) { this.lostLocation = lostLocation; }

    public LocalDate getLostDate() { return lostDate; }
    public void setLostDate(LocalDate lostDate) { this.lostDate = lostDate; }

    public String getLostDescription() { return lostDescription; }
    public void setLostDescription(String lostDescription) { this.lostDescription = lostDescription; }

    public Long getLostReportedById() { return lostReportedById; }
    public void setLostReportedById(Long lostReportedById) { this.lostReportedById = lostReportedById; }

    public String getLostReportedByName() { return lostReportedByName; }
    public void setLostReportedByName(String lostReportedByName) { this.lostReportedByName = lostReportedByName; }

    public Long getFoundItemId() { return foundItemId; }
    public void setFoundItemId(Long foundItemId) { this.foundItemId = foundItemId; }

    public String getFoundItemName() { return foundItemName; }
    public void setFoundItemName(String foundItemName) { this.foundItemName = foundItemName; }

    public String getFoundCategory() { return foundCategory; }
    public void setFoundCategory(String foundCategory) { this.foundCategory = foundCategory; }

    public String getFoundColor() { return foundColor; }
    public void setFoundColor(String foundColor) { this.foundColor = foundColor; }

    public String getFoundLocation() { return foundLocation; }
    public void setFoundLocation(String foundLocation) { this.foundLocation = foundLocation; }

    public LocalDate getFoundDate() { return foundDate; }
    public void setFoundDate(LocalDate foundDate) { this.foundDate = foundDate; }

    public String getFoundDescription() { return foundDescription; }
    public void setFoundDescription(String foundDescription) { this.foundDescription = foundDescription; }

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