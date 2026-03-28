package com.example.tasklist.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.tasklist.entities.SubTasks;

public interface SubtaskRepo extends MongoRepository<String, SubTasks> {

}
