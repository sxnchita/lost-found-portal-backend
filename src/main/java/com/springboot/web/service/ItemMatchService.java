package com.springboot.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.web.entity.FoundItem;
import com.springboot.web.entity.ItemMatch;
import com.springboot.web.entity.LostItem;
import com.springboot.web.repository.FoundItemRepository;
import com.springboot.web.repository.ItemMatchRepository;
import com.springboot.web.repository.LostItemRepository;

@Service
public class ItemMatchService {

    @Autowired
    private ItemMatchRepository itemMatchRepository;

    @Autowired
    private LostItemRepository lostItemRepository;

    @Autowired
    private FoundItemRepository foundItemRepository;

    public ItemMatch createMatch(Long lostItemId, Long foundItemId) {

        LostItem lostItem = lostItemRepository.findById(lostItemId).orElse(null);
        FoundItem foundItem = foundItemRepository.findById(foundItemId).orElse(null);

        if (lostItem == null || foundItem == null) {
            return null;
        }

        int score = calculateMatchScore(lostItem, foundItem);

        ItemMatch itemMatch = new ItemMatch();
        itemMatch.setLostItem(lostItem);
        itemMatch.setFoundItem(foundItem);
        itemMatch.setMatchScore(score);
        itemMatch.setMatchLevel(getMatchLevel(score));
        itemMatch.setMatchStatus("PENDING_ADMIN_REVIEW");

        return itemMatchRepository.save(itemMatch);
    }

    public ItemMatch approveMatch(Long matchId) {

        ItemMatch itemMatch = itemMatchRepository.findById(matchId).orElse(null);

        if (itemMatch == null) {
            return null;
        }

        itemMatch.setMatchStatus("APPROVED");

        return itemMatchRepository.save(itemMatch);
    }

    public ItemMatch rejectMatch(Long matchId) {

        ItemMatch itemMatch = itemMatchRepository.findById(matchId).orElse(null);

        if (itemMatch == null) {
            return null;
        }

        itemMatch.setMatchStatus("REJECTED");

        return itemMatchRepository.save(itemMatch);
    }

    private int calculateMatchScore(LostItem lostItem, FoundItem foundItem) {

        int score = 0;

        if (lostItem.getItemName() != null && foundItem.getItemName() != null &&
                lostItem.getItemName().equalsIgnoreCase(foundItem.getItemName())) {
            score += 40;
        }

        if (lostItem.getCategory() != null && foundItem.getCategory() != null &&
                lostItem.getCategory().equalsIgnoreCase(foundItem.getCategory())) {
            score += 20;
        }

        if (lostItem.getColor() != null && foundItem.getColor() != null &&
                lostItem.getColor().equalsIgnoreCase(foundItem.getColor())) {
            score += 15;
        }

        if (lostItem.getLostLocation() != null && foundItem.getFoundLocation() != null &&
                lostItem.getLostLocation().equalsIgnoreCase(foundItem.getFoundLocation())) {
            score += 15;
        }

        if (lostItem.getModel() != null && foundItem.getModel() != null &&
                lostItem.getModel().equalsIgnoreCase(foundItem.getModel())) {
            score += 10;
        }

        return score;
    }

    private String getMatchLevel(int score) {
        if (score >= 80) {
            return "HIGH";
        } else if (score >= 50) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }
}