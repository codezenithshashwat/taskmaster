package com.example.taskmaster.controller;

import com.example.taskmaster.dto.TaskRequest;
import com.example.taskmaster.entity.Priority;
import com.example.taskmaster.entity.Task;
import com.example.taskmaster.entity.TaskStatus;
import com.example.taskmaster.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createdTask(@Valid @RequestBody TaskRequest request) {
        Task createdTask = taskService.createTask(request);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Task>> getAllTasks(@PageableDefault(size = 5, sort = "createdAt") Pageable pageable) {
        Page<Task> tasks = taskService.getAllTasks(pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task with id " + id + " deleted successfully");
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody TaskRequest request) {
        return taskService.updateTask(id, request);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Task>> searchTasks(@RequestParam String keyword,
                                                   @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<Task> tasks = taskService.searchTasks(keyword, pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Task>> getTasksByStatus(@PathVariable TaskStatus status,
                                                        @PageableDefault(size = 5, sort = "createdAt") Pageable pageable) {
        Page<Task> tasks = taskService.getTasksByStatus(status, pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<Page<Task>> getTasksByPriority(@PathVariable Priority priority,
                                                          @PageableDefault(size = 5, sort = "createdAt") Pageable pageable) {
        Page<Task> tasks = taskService.getTasksByPriority(priority, pageable);
        return ResponseEntity.ok(tasks);
    }
}
