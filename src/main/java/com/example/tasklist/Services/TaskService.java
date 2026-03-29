package com.example.tasklist.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tasklist.Repositories.TaskRepo;
import com.example.tasklist.entities.Tasks;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    public List<Tasks> get_tasks_by_user(String userId) {
        return taskRepo.findByUserId(userId);
    }

    public Optional<Tasks> get_task_by_id(String id) {
        return taskRepo.findById(id);
    }

    public Tasks save_task(Tasks task) {
        return taskRepo.save(task);
    }

    public void delete_task(String id) {
        taskRepo.deleteById(id);
    }

    public List<Tasks> getall() {
        return taskRepo.findAll();
    }
}