package com.example.tasklist.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "subtasks")
public class SubTasks {
    @Id
    private String Id;
    private String taskId; // refer to task primary key
    private String content;
}
