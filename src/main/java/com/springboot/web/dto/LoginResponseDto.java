package com.springboot.web.dto;

public class LoginResponseDto {

    private String message;
    private String email;
    private String role;
    private String token;
    private Long userId;

    public LoginResponseDto(
            String message,
            String email,
            String role,
            String token,
            Long userId
    ) {
        this.message = message;
        this.email = email;
        this.role = role;
        this.token = token;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }
}