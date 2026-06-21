package com.springboot.web.controller;

import com.springboot.web.dto.ForgotPasswordDto;
import com.springboot.web.dto.LoginRequestDto;
import com.springboot.web.dto.LoginResponseDto;
import com.springboot.web.entity.User;
import com.springboot.web.repository.UserRepository;
import com.springboot.web.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {

        String email = loginRequestDto.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // TEMP DEMO FIX: password check bypassed
        System.out.println("Temporary demo login bypass active");

        String role = user.getRole() != null
                ? user.getRole().getRoleName()
                : "STUDENT";

        String token = jwtService.generateToken(user.getEmail(), role);

        return new LoginResponseDto(
                "Login successful",
                user.getEmail(),
                role,
                token,
                user.getUserId()
        );
    }

    @PutMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordDto dto) {

        String email = dto.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        return "Password reset successfully";
    }

    @GetMapping("/ping")
    public String ping() {
        return "LATEST_BACKEND_CODE";
    }
}