package com.example.tasklist.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tasklist.Services.TaskService;
import com.example.tasklist.Services.UserService;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public Map<String, Object> overview() {
        return Map.of(
                "users", userService.get_all_users(),
                "tasks", taskService.getall());
    }
}
