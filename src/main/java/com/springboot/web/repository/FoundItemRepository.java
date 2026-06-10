package com.springboot.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.web.entity.FoundItem;

public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {

    List<FoundItem> findByItemNameContainingIgnoreCase(String keyword);

    List<FoundItem> findByCategoryIgnoreCase(String category);

    List<FoundItem> findByFoundLocationContainingIgnoreCase(String location);

    List<FoundItem> findByStatusIgnoreCase(String status);
}