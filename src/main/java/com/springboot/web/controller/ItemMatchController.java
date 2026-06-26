package com.springboot.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.springboot.web.dto.ItemMatchResponseDto;
import com.springboot.web.entity.ItemMatch;
import com.springboot.web.service.ItemMatchService;

@RestController
@RequestMapping("/matches")
public class ItemMatchController {

    @Autowired
    private ItemMatchService itemMatchService;

    @PostMapping
    public ItemMatchResponseDto createMatch(
            @RequestParam Long lostItemId,
            @RequestParam Long foundItemId) {
        return convertToDto(itemMatchService.createMatch(lostItemId, foundItemId));
    }

    @GetMapping
    public List<ItemMatchResponseDto> getAllMatches() {
        return itemMatchService.getAllMatches()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/student/{userId}")
    public List<ItemMatchResponseDto> getApprovedMatchesByStudent(@PathVariable Long userId) {
        return itemMatchService.getApprovedMatchesByStudent(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/approve")
    public ItemMatchResponseDto approveMatch(@PathVariable Long id) {
        return convertToDto(itemMatchService.approveMatch(id));
    }

    @PutMapping("/{id}/reject")
    public ItemMatchResponseDto rejectMatch(@PathVariable Long id) {
        return convertToDto(itemMatchService.rejectMatch(id));
    }

    private ItemMatchResponseDto convertToDto(ItemMatch match) {

        ItemMatchResponseDto dto = new ItemMatchResponseDto();

        dto.setMatchId(match.getMatchId());
        dto.setMatchScore(match.getMatchScore());
        dto.setMatchLevel(match.getMatchLevel());
        dto.setMatchStatus(match.getMatchStatus());
        dto.setCreatedAt(match.getCreatedAt());

        // LOST ITEM
        if (match.getLostItem() != null) {
            dto.setLostItemId(match.getLostItem().getLostItemId());
            dto.setLostItemName(match.getLostItem().getItemName());
            dto.setLostCategory(match.getLostItem().getCategory());
            dto.setLostColor(match.getLostItem().getColor());
            dto.setLostLocation(match.getLostItem().getLostLocation());
            dto.setLostDate(match.getLostItem().getLostDate());
            dto.setLostDescription(match.getLostItem().getDescription());

            if (match.getLostItem().getReportedBy() != null) {
                dto.setLostReportedById(
                        match.getLostItem().getReportedBy().getUserId());
                dto.setLostReportedByName(
                        match.getLostItem().getReportedBy().getFullName());
            }
        }

        // FOUND ITEM
        if (match.getFoundItem() != null) {
            dto.setFoundItemId(match.getFoundItem().getFoundItemId());
            dto.setFoundItemName(match.getFoundItem().getItemName());
            dto.setFoundCategory(match.getFoundItem().getCategory());
            dto.setFoundColor(match.getFoundItem().getColor());
            dto.setFoundLocation(match.getFoundItem().getFoundLocation());
            dto.setFoundDate(match.getFoundItem().getFoundDate());
            dto.setFoundDescription(match.getFoundItem().getDescription());

            if (match.getFoundItem().getReportedBy() != null) {
                dto.setFoundReportedById(
                        match.getFoundItem().getReportedBy().getUserId());
                dto.setFoundReportedByName(
                        match.getFoundItem().getReportedBy().getFullName());
            }
        }

        return dto;
    }
}