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

        if (same(lostItem.getCategory(), foundItem.getCategory())) score += 25;
        if (same(lostItem.getColor(), foundItem.getColor())) score += 20;
        if (same(lostItem.getModel(), foundItem.getModel())) score += 10;

        score += similarityScore(lostItem.getItemName(), foundItem.getItemName(), 25);
        score += similarityScore(lostItem.getLostLocation(), foundItem.getFoundLocation(), 10);
        score += similarityScore(lostItem.getDescription(), foundItem.getDescription(), 10);

        return Math.min(score, 100);
    }

    private int similarityScore(String a, String b, int maxScore) {
        if (a == null || b == null) return 0;

        String[] wordsA = a.toLowerCase().trim().split("\\s+");
        String textB = b.toLowerCase();

        int matches = 0;

        for (String word : wordsA) {
            if (word.length() > 2 && textB.contains(word)) {
                matches++;
            }
        }

        if (wordsA.length == 0) return 0;

        return (matches * maxScore) / wordsA.length;
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