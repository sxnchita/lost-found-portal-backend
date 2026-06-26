package com.springboot.web.service;

import com.springboot.web.dto.LostItemRequestDto;
import com.springboot.web.dto.LostItemResponseDto;
import com.springboot.web.entity.LostItem;
import com.springboot.web.entity.User;
import com.springboot.web.exception.ResourceNotFoundException;
import com.springboot.web.repository.LostItemRepository;
import com.springboot.web.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LostItemService {

    @Autowired
    private LostItemRepository lostItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditLogService auditLogService;

    public LostItemResponseDto saveLostItem(LostItemRequestDto dto) {
        LostItem lostItem = new LostItem();

        lostItem.setItemName(dto.getItemName());
        lostItem.setCategory(dto.getCategory());
        lostItem.setColor(dto.getColor());
        lostItem.setModel(dto.getModel());
        lostItem.setDescription(dto.getDescription());
        lostItem.setLostLocation(dto.getLostLocation());
        lostItem.setLostDate(dto.getLostDate());
        lostItem.setImageUrl(dto.getImageUrl());
        lostItem.setSpecialFeatures(dto.getSpecialFeatures());
        lostItem.setStatus("PENDING_APPROVAL");

        if (dto.getReportedById() != null) {
            User user = userRepository.findById(dto.getReportedById())
                    .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

            lostItem.setReportedBy(user);
        }

        LostItem savedItem = lostItemRepository.save(lostItem);

        auditLogService.createAuditLog(
                null,
                "LOST_ITEM_REPORTED",
                "LostItem",
                savedItem.getLostItemId()
        );

        return convertToResponseDto(savedItem);
    }

    public List<LostItemResponseDto> getAllLostItems() {
        return lostItemRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "lostItems", key = "#id")
    public LostItemResponseDto getLostItemById(Long id) {
        LostItem lostItem = lostItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lost Item Not Found"));

        return convertToResponseDto(lostItem);
    }

    @CacheEvict(value = "lostItems", key = "#lostItemId")
    public LostItemResponseDto approveLostItem(Long lostItemId) {
        LostItem lostItem = lostItemRepository.findById(lostItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Lost Item Not Found"));

        lostItem.setStatus("ACTIVE");
        LostItem updatedItem = lostItemRepository.save(lostItem);

        auditLogService.createAuditLog(null, "LOST_ITEM_APPROVED", "LostItem", lostItemId);

        return convertToResponseDto(updatedItem);
    }

    @CacheEvict(value = "lostItems", key = "#lostItemId")
    public LostItemResponseDto rejectLostItem(Long lostItemId) {
        LostItem lostItem = lostItemRepository.findById(lostItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Lost Item Not Found"));

        lostItem.setStatus("REJECTED");
        LostItem updatedItem = lostItemRepository.save(lostItem);

        auditLogService.createAuditLog(null, "LOST_ITEM_REJECTED", "LostItem", lostItemId);

        return convertToResponseDto(updatedItem);
    }

    public List<LostItemResponseDto> searchLostItemsByKeyword(String keyword) {
        return lostItemRepository.findByItemNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<LostItemResponseDto> filterLostItemsByCategory(String category) {
        return lostItemRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<LostItemResponseDto> filterLostItemsByLocation(String location) {
        return lostItemRepository.findByLostLocationContainingIgnoreCase(location)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<LostItemResponseDto> filterLostItemsByStatus(String status) {
        return lostItemRepository.findByStatusIgnoreCase(status)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private LostItemResponseDto convertToResponseDto(LostItem lostItem) {
        LostItemResponseDto dto = new LostItemResponseDto();

        dto.setLostItemId(lostItem.getLostItemId());
        dto.setItemName(lostItem.getItemName());
        dto.setCategory(lostItem.getCategory());
        dto.setColor(lostItem.getColor());
        dto.setModel(lostItem.getModel());
        dto.setDescription(lostItem.getDescription());
        dto.setLostLocation(lostItem.getLostLocation());
        dto.setLostDate(lostItem.getLostDate());
        dto.setImageUrl(lostItem.getImageUrl());
        dto.setSpecialFeatures(lostItem.getSpecialFeatures());
        dto.setStatus(lostItem.getStatus());
        dto.setCreatedAt(lostItem.getCreatedAt());

        if (lostItem.getReportedBy() != null) {
            dto.setReportedById(lostItem.getReportedBy().getUserId());
            dto.setReportedByName(lostItem.getReportedBy().getFullName());
        }

        return dto;
    }
}