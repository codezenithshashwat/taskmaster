package com.example.taskmaster.controller;


import com.example.taskmaster.dto.TaskRequest;
import com.example.taskmaster.entity.Task;
import com.example.taskmaster.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
//import org.hibernate.query.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createdTask(@Valid @RequestBody TaskRequest request) {
        Task createdTask= taskService.createTask(request);

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<Task>> getAllTasks(@PageableDefault(size=5, sort="createdAt") Pageable pageable) {
        Page<Task> tasks= taskService.getAllTasks(pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}") // id is path variable
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        Task task= taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
//        return ResponseEntity.noContent().build();
        return ResponseEntity.ok("Task with id " + id + " deleted successfully");
    }


    @PutMapping
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        Task updatedTask= taskService.updateTask(id, request);
        return ResponseEntity.ok(updatedTask);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Task>> searchTasks(@RequestParam String keyword, @PageableDefault(size=5, sort="id") Pageable pageable) {
       Page<Task> tasks= taskService.searchTasks(keyword, pageable);
        return ResponseEntity.ok(tasks);
    }
}
