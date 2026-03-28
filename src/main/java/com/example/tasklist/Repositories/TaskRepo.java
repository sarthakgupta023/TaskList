package com.example.tasklist.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tasklist.entities.Tasks;

public interface TaskRepo extends MongoRepository<Tasks, String> {
    List<Tasks> findByUserId(String userId); // get all tasks of a user
}