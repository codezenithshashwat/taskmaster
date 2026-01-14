package com.example.taskmaster;

import com.example.taskmaster.entity.Task;
import com.example.taskmaster.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskmasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmasterApplication.class, args);
	}
    @Bean
	public CommandLineRunner dataLoader (TaskRepository repository){
		return args -> {
			if(repository.count()==0){
				for(int i=0;i<20;i++){
					Task task= Task.builder()
							.title("Dummy task "+ i)
							.description("Generated automatically")
							.build();
					repository.save(task);
				}
				System.out.println("Generated 20 tasks!");
			}
		};
	}
}
