package com.example.tasklist.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tasklist.entities.Users;

public interface UserRepo extends MongoRepository<Users, String> {
    Optional<Users> findByEmail(String email);
}