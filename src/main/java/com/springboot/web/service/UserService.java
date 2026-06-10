package com.springboot.web.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.web.dto.UserRequestDto;
import com.springboot.web.dto.UserResponseDto;
import com.springboot.web.entity.User;
import com.springboot.web.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDto saveUser(UserRequestDto userRequestDto) {

        User user = new User();

        user.setFullName(userRequestDto.getFullName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setRollNumber(userRequestDto.getRollNumber());
        user.setDepartment(userRequestDto.getDepartment());
        user.setPhone(userRequestDto.getPhone());

        User savedUser = userRepository.save(user);

        return convertToResponseDto(savedUser);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        return convertToResponseDto(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponseDto convertToResponseDto(User user) {

        UserResponseDto dto = new UserResponseDto();

        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setRollNumber(user.getRollNumber());
        dto.setDepartment(user.getDepartment());
        dto.setPhone(user.getPhone());
        dto.setIsActive(user.getIsActive());

        if (user.getRole() != null) {
            dto.setRole(user.getRole().getRoleName());
        }

        return dto;
    }
}