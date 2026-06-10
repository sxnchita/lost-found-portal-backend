package com.springboot.web.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.web.dto.NotificationResponseDto;
import com.springboot.web.entity.Notification;
import com.springboot.web.entity.User;
import com.springboot.web.exception.ResourceNotFoundException;
import com.springboot.web.repository.NotificationRepository;
import com.springboot.web.repository.UserRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public NotificationResponseDto createNotification(
            Long userId,
            String title,
            String message,
            String notificationType) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(notificationType);

        Notification savedNotification = notificationRepository.save(notification);

        return convertToResponseDto(savedNotification);
    }

    public List<NotificationResponseDto> getNotificationsByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        return notificationRepository.findAll()
                .stream()
                .filter(notification -> notification.getUser().getUserId().equals(user.getUserId()))
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public NotificationResponseDto markAsRead(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification Not Found"));

        notification.setIsRead(true);

        Notification updatedNotification = notificationRepository.save(notification);

        return convertToResponseDto(updatedNotification);
    }

    private NotificationResponseDto convertToResponseDto(Notification notification) {

        NotificationResponseDto dto = new NotificationResponseDto();

        dto.setNotificationId(notification.getNotificationId());

        if (notification.getUser() != null) {
            dto.setUserId(notification.getUser().getUserId());
        }

        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setNotificationType(notification.getNotificationType());
        dto.setIsRead(notification.getIsRead());
        dto.setCreatedAt(notification.getCreatedAt());

        return dto;
    }
}