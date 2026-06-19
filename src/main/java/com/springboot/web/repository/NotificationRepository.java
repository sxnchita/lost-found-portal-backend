package com.springboot.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.web.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser_UserIdOrderByCreatedAtDesc(Long userId);
}