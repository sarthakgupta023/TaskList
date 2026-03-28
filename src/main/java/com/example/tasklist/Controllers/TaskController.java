package com.example.tasklist.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin("*")
@RequestMapping("tasks")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Tasks>> getAllTasks() {
        return ResponseEntity.ok(taskService.get_all_tasks());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTasksByUser(@PathVariable String userId) {
        if (userService.user_exist_by_id(userId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + userId);
        }
        return ResponseEntity.ok(taskService.get_tasks_by_user(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id) {
        Optional<Tasks> task = taskService.get_task_by_id(id);
        if (task.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task not found with id: " + id);
        }
        return ResponseEntity.ok(task.get());
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Tasks task) {

        if (userService.user_exist_by_id(task.getUserId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + task.getUserId());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.save_task(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id, @RequestBody Tasks updatedTask) {
        Optional<Tasks> temp = taskService.get_task_by_id(id);
        if (temp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task not found with id: " + id);
        }
        updatedTask.setId(id);
        return ResponseEntity.ok(taskService.save_task(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        Optional<Tasks> temp = taskService.get_task_by_id(id);
        if (temp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task not found with id: " + id);
        }
        taskService.delete_task(id);
        return ResponseEntity.ok("Task deleted successfully");
    }
}