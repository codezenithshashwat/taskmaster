package com.example.taskmaster.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TaskRequest(
        @NotBlank(message = "Title cannot be blank")
        String title, String description,
        @FutureOrPresent(message= "Due date cannot be in the past")
        LocalDateTime dueDate,
        boolean completed
) {
}
