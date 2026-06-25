package com.springboot.web.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.web.dto.UserRequestDto;
import com.springboot.web.dto.UserResponseDto;
import com.springboot.web.entity.Role;
import com.springboot.web.entity.User;
import com.springboot.web.exception.ResourceNotFoundException;
import com.springboot.web.repository.RoleRepository;
import com.springboot.web.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDto saveUser(UserRequestDto userRequestDto) {

        String email = userRequestDto.getEmail().trim().toLowerCase();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Role studentRole = roleRepository.findByRoleName("STUDENT")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setRoleName("STUDENT");
                    return roleRepository.save(role);
                });

        User user = new User();
        user.setFullName(userRequestDto.getFullName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRollNumber(userRequestDto.getRollNumber());
        user.setDepartment(userRequestDto.getDepartment());
        user.setPhone(userRequestDto.getPhone());
        user.setRole(studentRole);
        user.setIsActive(true);

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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

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