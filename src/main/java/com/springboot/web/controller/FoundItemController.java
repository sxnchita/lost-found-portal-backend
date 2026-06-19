package com.springboot.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.springboot.web.dto.FoundItemRequestDto;
import com.springboot.web.dto.FoundItemResponseDto;
import com.springboot.web.service.FoundItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/found-items")
@CrossOrigin(origins = "http://localhost:5173")
public class FoundItemController {

    @Autowired
    private FoundItemService foundItemService;

    @PostMapping
    public FoundItemResponseDto createFoundItem(@Valid @RequestBody FoundItemRequestDto foundItemRequestDto) {
        return foundItemService.saveFoundItem(foundItemRequestDto);
    }

    @GetMapping
    public List<FoundItemResponseDto> getAllFoundItems() {
        return foundItemService.getAllFoundItems();
    }

    @GetMapping("/{id}")
    public FoundItemResponseDto getFoundItemById(@PathVariable Long id) {
        return foundItemService.getFoundItemById(id);
    }

    @PutMapping("/{id}/approve")
    public FoundItemResponseDto approveFoundItem(@PathVariable Long id) {
        return foundItemService.approveFoundItem(id);
    }

    @PutMapping("/{id}/reject")
    public FoundItemResponseDto rejectFoundItem(@PathVariable Long id) {
        return foundItemService.rejectFoundItem(id);
    }

    @GetMapping("/search")
    public List<FoundItemResponseDto> searchFoundItems(@RequestParam String keyword) {
        return foundItemService.searchFoundItemsByKeyword(keyword);
    }

    @GetMapping("/filter/category")
    public List<FoundItemResponseDto> filterByCategory(@RequestParam String category) {
        return foundItemService.filterFoundItemsByCategory(category);
    }

    @GetMapping("/filter/location")
    public List<FoundItemResponseDto> filterByLocation(@RequestParam String location) {
        return foundItemService.filterFoundItemsByLocation(location);
    }

    @GetMapping("/filter/status")
    public List<FoundItemResponseDto> filterByStatus(@RequestParam String status) {
        return foundItemService.filterFoundItemsByStatus(status);
    }
}