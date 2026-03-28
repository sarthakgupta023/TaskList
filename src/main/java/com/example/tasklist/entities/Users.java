package com.example.tasklist.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "users")
public class Users {
    @Id
    private String Id;

    private String name;
    private String email;
    private String password;
}
