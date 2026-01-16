package com.example.taskmaster.repository;
import com.example.taskmaster.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page <Task> findByTitleContainingIgnoreCase(String keyword, Pageable pagable);
}
