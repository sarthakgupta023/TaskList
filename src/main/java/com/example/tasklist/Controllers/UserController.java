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

import com.example.tasklist.Services.UserService;
import com.example.tasklist.entities.Users;

@CrossOrigin("http://localhost:5173")
@RequestMapping("users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Users>> getallUser() {
        List<Users> users = userService.get_all_users();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping
    public ResponseEntity<?> save_user(@RequestBody Users user) {
        Optional<Users> temp = userService.user_exist(user.getEmail());
        if (temp.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User already exists with this email");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save_user(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        Optional<Users> temp = userService.user_exist_by_id(id);
        if (temp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + id);
        }
        userService.delete_user(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody Users updatedUser) {
        Optional<Users> temp = userService.user_exist_by_id(id);
        if (temp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + id);
        }
        updatedUser.setId(id);
        Users saved = userService.save_user(updatedUser);
        return ResponseEntity.ok(saved);
    }

}
