package com.example.tasklist.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tasklist.Security.JwtUtil;
import com.example.tasklist.entities.Users;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Users register(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save_user(user);
    }

    public String login(String email, String password) {
        Optional<Users> temp = userService.user_exist(email);

        if (temp.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Users user = temp.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtUtil.generateToken(user.getEmail());
    }
}