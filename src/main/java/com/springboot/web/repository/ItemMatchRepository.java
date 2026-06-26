package com.springboot.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.web.entity.ItemMatch;

public interface ItemMatchRepository extends JpaRepository<ItemMatch, Long> {

    Optional<ItemMatch> findByLostItem_LostItemIdAndFoundItem_FoundItemId(
            Long lostItemId,
            Long foundItemId
    );

    List<ItemMatch> findByMatchStatusAndLostItem_ReportedBy_UserIdOrMatchStatusAndFoundItem_ReportedBy_UserId(
            String status1,
            Long lostUserId,
            String status2,
            Long foundUserId
    );

    List<ItemMatch> findByFoundItem_FoundItemId(Long foundItemId);
}