package com.example.taskmaster.dto;

import com.example.taskmaster.entity.Priority;
import com.example.taskmaster.entity.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TaskRequest(
        @NotBlank(message = "Title cannot be blank")
        String title,
        String description,
        @FutureOrPresent(message = "Due date cannot be in the past")
        LocalDateTime dueDate,
        boolean completed,
        Priority priority,
        TaskStatus status
) {}
