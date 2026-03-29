package com.example.tasklist.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tasklist.Services.TaskService;
import com.example.tasklist.Services.UserService;
import com.example.tasklist.entities.Tasks;
import com.example.tasklist.entities.Users;

@RequestMapping("tasks")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    private Optional<Users> currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.user_exist(email);
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        Optional<Users> user = currentUser();
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        return ResponseEntity.ok(taskService.get_tasks_by_user(user.get().getId()));
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Tasks task) {
        Optional<Users> user = currentUser();
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        task.setUserId(user.get().getId());
        task.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save_task(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id, @RequestBody Tasks updatedTask) {
        Optional<Tasks> existing = taskService.get_task_by_id(id);
        if (existing.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        updatedTask.setId(id);
        updatedTask.setUserId(existing.get().getUserId());
        return ResponseEntity.ok(taskService.save_task(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        Optional<Tasks> existing = taskService.get_task_by_id(id);
        if (existing.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        taskService.delete_task(id);
        return ResponseEntity.ok("Task deleted");
    }
}