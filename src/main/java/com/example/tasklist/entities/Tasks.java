package com.example.tasklist.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "tasks")
public class Tasks {
    @Id
    private String Id;
    private String userId; // foreign key, refer to primary key of user
    private String content;
}
