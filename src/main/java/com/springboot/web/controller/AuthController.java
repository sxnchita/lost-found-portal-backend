package com.springboot.web.controller;

import com.springboot.web.dto.ForgotPasswordDto;
import com.springboot.web.dto.LoginRequestDto;
import com.springboot.web.dto.LoginResponseDto;
import com.springboot.web.entity.User;
import com.springboot.web.repository.UserRepository;
import com.springboot.web.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

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
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        return "Password reset successfully";
    }
}