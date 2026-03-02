package com.example.taskmaster.dto;

public record AuthResponse(
        String token,
        String username,
        String email
) {}
