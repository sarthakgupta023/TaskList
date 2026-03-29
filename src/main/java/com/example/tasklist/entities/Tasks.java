package com.example.tasklist.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "tasks")
public class Tasks {
    @Id
    private String id;
    private String userId;
    private String title;
    private String content;
    private String status; // current
}