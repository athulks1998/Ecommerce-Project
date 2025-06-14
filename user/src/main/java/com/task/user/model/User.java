package com.task.user.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Athul KS
 * Represents a user in the system.
 * User entity for database.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_details")
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private List<Notification> notifications; 
    private String address;
}