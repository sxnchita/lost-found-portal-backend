package com.springboot.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.web.entity.LostItem;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {

    List<LostItem> findByItemNameContainingIgnoreCase(String keyword);

    List<LostItem> findByCategoryIgnoreCase(String category);

    List<LostItem> findByLostLocationContainingIgnoreCase(String location);

    List<LostItem> findByStatusIgnoreCase(String status);
}