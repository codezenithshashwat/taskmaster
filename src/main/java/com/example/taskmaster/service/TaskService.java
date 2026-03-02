package com.example.taskmaster.service;

import com.example.taskmaster.dto.TaskRequest;
import com.example.taskmaster.entity.Priority;
import com.example.taskmaster.entity.Task;
import com.example.taskmaster.entity.TaskStatus;
import com.example.taskmaster.entity.User;
import com.example.taskmaster.exception.ResourceNotFoundException;
import com.example.taskmaster.repository.TaskRepository;
import com.example.taskmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Task createTask(TaskRequest request) {
        User currentUser = getCurrentUser();
        Task newTask = Task.builder()
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .priority(request.priority() != null ? request.priority() : Priority.MEDIUM)
                .status(request.status() != null ? request.status() : TaskStatus.PENDING)
                .user(currentUser)
                .build();

        return taskRepository.save(newTask);
    }

    public Page<Task> getAllTasks(Pageable pageable) {
        User currentUser = getCurrentUser();
        return taskRepository.findByUserId(currentUser.getId(), pageable);
    }

    public Task getTaskById(Long id) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Task with id " + id + " not found");
        }
        return task;
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    public Task updateTask(Long id, TaskRequest request) {
        Task existingTask = getTaskById(id);

        existingTask.setTitle(request.title());
        existingTask.setDescription(request.description());
        existingTask.setDueDate(request.dueDate());
        existingTask.setCompleted(request.completed());
        if (request.priority() != null) {
            existingTask.setPriority(request.priority());
        }
        if (request.status() != null) {
            existingTask.setStatus(request.status());
        }
        if (request.completed()) {
            existingTask.setStatus(TaskStatus.COMPLETED);
        } else {
            existingTask.updateOverdueStatus();
        }

        return taskRepository.save(existingTask);
    }

    public Page<Task> searchTasks(String keyword, Pageable pageable) {
        User currentUser = getCurrentUser();
        return taskRepository.findByUserIdAndTitleContainingIgnoreCase(currentUser.getId(), keyword, pageable);
    }

    public Page<Task> getTasksByStatus(TaskStatus status, Pageable pageable) {
        User currentUser = getCurrentUser();
        return taskRepository.findByUserIdAndStatus(currentUser.getId(), status, pageable);
    }

    public Page<Task> getTasksByPriority(Priority priority, Pageable pageable) {
        User currentUser = getCurrentUser();
        return taskRepository.findByUserIdAndPriority(currentUser.getId(), priority, pageable);
    }
}
