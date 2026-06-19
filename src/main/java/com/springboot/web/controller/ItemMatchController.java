package com.springboot.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.springboot.web.entity.ItemMatch;
import com.springboot.web.service.ItemMatchService;

@RestController
@RequestMapping("/matches")
public class ItemMatchController {

    @Autowired
    private ItemMatchService itemMatchService;

    @PostMapping
    public ItemMatch createMatch(
            @RequestParam Long lostItemId,
            @RequestParam Long foundItemId) {
        return itemMatchService.createMatch(lostItemId, foundItemId);
    }

    @GetMapping
    public List<ItemMatch> getAllMatches() {
        return itemMatchService.getAllMatches();
    }
    
    @GetMapping("/student/{userId}")
    public List<ItemMatch> getApprovedMatchesByStudent(@PathVariable Long userId) {
        return itemMatchService.getApprovedMatchesByStudent(userId);
    }

    @PutMapping("/{id}/approve")
    public ItemMatch approveMatch(@PathVariable Long id) {
        return itemMatchService.approveMatch(id);
    }

    @PutMapping("/{id}/reject")
    public ItemMatch rejectMatch(@PathVariable Long id) {
        return itemMatchService.rejectMatch(id);
    }
}