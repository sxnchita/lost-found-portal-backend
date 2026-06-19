package com.springboot.web.service;

import java.time.LocalDateTime;
import java.util.List;

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
        var existingMatch =
                itemMatchRepository.findByLostItem_LostItemIdAndFoundItem_FoundItemId(
                        lostItemId,
                        foundItemId
                );

        if (existingMatch.isPresent()) {
            return existingMatch.get();
        }

        LostItem lostItem = lostItemRepository.findById(lostItemId)
                .orElseThrow(() -> new RuntimeException("Lost item not found"));

        FoundItem foundItem = foundItemRepository.findById(foundItemId)
                .orElseThrow(() -> new RuntimeException("Found item not found"));

        int score = calculateMatchScore(lostItem, foundItem);

        ItemMatch itemMatch = new ItemMatch();
        itemMatch.setLostItem(lostItem);
        itemMatch.setFoundItem(foundItem);
        itemMatch.setMatchScore(score);
        itemMatch.setMatchLevel(getMatchLevel(score));
        itemMatch.setMatchStatus("PENDING_ADMIN_REVIEW");

        return itemMatchRepository.save(itemMatch);
    }

    public List<ItemMatch> getAllMatches() {
        return itemMatchRepository.findAll();
    }

    public List<ItemMatch> getApprovedMatchesByStudent(Long userId) {
        return itemMatchRepository.findAll()
                .stream()
                .filter(match -> "APPROVED".equals(match.getMatchStatus()))
                .toList();
    }

    public ItemMatch approveMatch(Long matchId) {
        ItemMatch itemMatch = itemMatchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        itemMatch.setMatchStatus("APPROVED");
        itemMatch.setApprovedAt(LocalDateTime.now());

        return itemMatchRepository.save(itemMatch);
    }

    public ItemMatch rejectMatch(Long matchId) {
        ItemMatch itemMatch = itemMatchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        itemMatch.setMatchStatus("REJECTED");

        return itemMatchRepository.save(itemMatch);
    }

    private int calculateMatchScore(LostItem lostItem, FoundItem foundItem) {
        int score = 0;

        if (same(lostItem.getItemName(), foundItem.getItemName())) score += 40;
        if (same(lostItem.getCategory(), foundItem.getCategory())) score += 20;
        if (same(lostItem.getColor(), foundItem.getColor())) score += 15;
        if (same(lostItem.getLostLocation(), foundItem.getFoundLocation())) score += 15;
        if (same(lostItem.getModel(), foundItem.getModel())) score += 10;

        return score;
    }

    private boolean same(String a, String b) {
        return a != null && b != null && a.trim().equalsIgnoreCase(b.trim());
    }

    private String getMatchLevel(int score) {
        if (score >= 80) return "HIGH";
        if (score >= 50) return "MEDIUM";
        return "LOW";
    }
}