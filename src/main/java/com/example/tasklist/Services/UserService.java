package com.example.tasklist.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tasklist.Repositories.UserRepo;
import com.example.tasklist.entities.Users;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public List<Users> get_all_users() {
        return userRepo.findAll();
    }

    public Optional<Users> user_exist(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<Users> user_exist_by_id(String id) {
        return userRepo.findById(id);
    }

    public Users save_user(Users user) {
        return userRepo.save(user);
    }

    public void delete_user(String id) {
        userRepo.deleteById(id);
    }
}