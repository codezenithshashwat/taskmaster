package com.example.taskmaster.service;

import com.example.taskmaster.dto.TaskRequest;
import com.example.taskmaster.entity.Task;
import com.example.taskmaster.exception.ResourceNotFoundException;
import com.example.taskmaster.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    public Task createTask(TaskRequest request){
        Task newTask= Task.builder()
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .build();

        return taskRepository.save(newTask);
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Task with id " + id + " not found"));
    }

    public void deleteTask(Long id){
        if(!taskRepository.existsById(id)){
            throw new ResourceNotFoundException("Task with id " + id + " not found");
        }
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, TaskRequest request){
        Task existingTask= getTaskById(id);

        existingTask.setTitle(request.title());
        existingTask.setDescription(request.description());
        existingTask.setDueDate(request.dueDate());

        return taskRepository.save(existingTask);
    }
}
