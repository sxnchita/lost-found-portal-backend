package com.springboot.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.springboot.web.dto.LostItemRequestDto;
import com.springboot.web.dto.LostItemResponseDto;
import com.springboot.web.service.LostItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/lost-items")
public class LostItemController {

    @Autowired
    private LostItemService lostItemService;

    @PostMapping
    public LostItemResponseDto createLostItem(@Valid @RequestBody LostItemRequestDto lostItemRequestDto) {
        return lostItemService.saveLostItem(lostItemRequestDto);
    }

    @GetMapping
    public List<LostItemResponseDto> getAllLostItems() {
        return lostItemService.getAllLostItems();
    }

    @GetMapping("/{id}")
    public LostItemResponseDto getLostItemById(@PathVariable Long id) {
        return lostItemService.getLostItemById(id);
    }

    @PutMapping("/{id}/approve")
    public LostItemResponseDto approveLostItem(@PathVariable Long id) {
        return lostItemService.approveLostItem(id);
    }

    @GetMapping("/search")
    public List<LostItemResponseDto> searchLostItems(@RequestParam String keyword) {
        return lostItemService.searchLostItemsByKeyword(keyword);
    }

    @GetMapping("/filter/category")
    public List<LostItemResponseDto> filterByCategory(@RequestParam String category) {
        return lostItemService.filterLostItemsByCategory(category);
    }

    @GetMapping("/filter/location")
    public List<LostItemResponseDto> filterByLocation(@RequestParam String location) {
        return lostItemService.filterLostItemsByLocation(location);
    }

    @GetMapping("/filter/status")
    public List<LostItemResponseDto> filterByStatus(@RequestParam String status) {
        return lostItemService.filterLostItemsByStatus(status);
    }
}