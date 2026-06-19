package com.springboot.web.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.web.dto.FoundItemRequestDto;
import com.springboot.web.dto.FoundItemResponseDto;
import com.springboot.web.entity.FoundItem;
import com.springboot.web.entity.LostItem;
import com.springboot.web.exception.ResourceNotFoundException;
import com.springboot.web.repository.FoundItemRepository;
import com.springboot.web.repository.LostItemRepository;

@Service
public class FoundItemService {

    @Autowired
    private FoundItemRepository foundItemRepository;

    @Autowired
    private LostItemRepository lostItemRepository;

    @Autowired
    private ItemMatchService itemMatchService;

    public FoundItemResponseDto saveFoundItem(FoundItemRequestDto dto) {
        FoundItem foundItem = new FoundItem();

        foundItem.setItemName(dto.getItemName());
        foundItem.setCategory(dto.getCategory());
        foundItem.setColor(dto.getColor());
        foundItem.setModel(dto.getModel());
        foundItem.setDescription(dto.getDescription());
        foundItem.setFoundLocation(dto.getFoundLocation());
        foundItem.setFoundDate(dto.getFoundDate());
        foundItem.setImageUrl(dto.getImageUrl());
        foundItem.setAdditionalNotes(dto.getAdditionalNotes());
        foundItem.setStatus("PENDING_APPROVAL");

        FoundItem savedItem = foundItemRepository.save(foundItem);

        List<LostItem> lostItems = lostItemRepository.findAll();

        for (LostItem lostItem : lostItems) {
            itemMatchService.createMatch(
                    lostItem.getLostItemId(),
                    savedItem.getFoundItemId()
            );
        }

        return convertToResponseDto(savedItem);
    }

    public List<FoundItemResponseDto> getAllFoundItems() {
        return foundItemRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public FoundItemResponseDto getFoundItemById(Long id) {
        FoundItem foundItem = foundItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Found Item Not Found"));

        return convertToResponseDto(foundItem);
    }

    public FoundItemResponseDto approveFoundItem(Long foundItemId) {
        FoundItem foundItem = foundItemRepository.findById(foundItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Found Item Not Found"));

        foundItem.setStatus("ACTIVE");

        FoundItem updatedItem = foundItemRepository.save(foundItem);
        return convertToResponseDto(updatedItem);
    }

    public FoundItemResponseDto rejectFoundItem(Long foundItemId) {
        FoundItem foundItem = foundItemRepository.findById(foundItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Found Item Not Found"));

        foundItem.setStatus("REJECTED");

        FoundItem updatedItem = foundItemRepository.save(foundItem);
        return convertToResponseDto(updatedItem);
    }

    public List<FoundItemResponseDto> searchFoundItemsByKeyword(String keyword) {
        return foundItemRepository.findByItemNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<FoundItemResponseDto> filterFoundItemsByCategory(String category) {
        return foundItemRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<FoundItemResponseDto> filterFoundItemsByLocation(String location) {
        return foundItemRepository.findByFoundLocationContainingIgnoreCase(location)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<FoundItemResponseDto> filterFoundItemsByStatus(String status) {
        return foundItemRepository.findByStatusIgnoreCase(status)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private FoundItemResponseDto convertToResponseDto(FoundItem foundItem) {
        FoundItemResponseDto dto = new FoundItemResponseDto();

        dto.setFoundItemId(foundItem.getFoundItemId());
        dto.setItemName(foundItem.getItemName());
        dto.setCategory(foundItem.getCategory());
        dto.setColor(foundItem.getColor());
        dto.setModel(foundItem.getModel());
        dto.setDescription(foundItem.getDescription());
        dto.setFoundLocation(foundItem.getFoundLocation());
        dto.setFoundDate(foundItem.getFoundDate());
        dto.setImageUrl(foundItem.getImageUrl());
        dto.setAdditionalNotes(foundItem.getAdditionalNotes());
        dto.setStatus(foundItem.getStatus());
        dto.setCreatedAt(foundItem.getCreatedAt());

        if (foundItem.getReportedBy() != null) {
            dto.setReportedById(foundItem.getReportedBy().getUserId());
            dto.setReportedByName(foundItem.getReportedBy().getFullName());
        }

        return dto;
    }
}