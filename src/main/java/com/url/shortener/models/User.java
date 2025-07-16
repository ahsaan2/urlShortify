package com.url.shortener.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// we want this class or entity to persist into the database
@Entity  // marks this class as JPA entity, (maps to a table in the database)
@Data  // lombok annotation: Generates getters, setters, toString, equals, hashcode, and more
@Table(name = "users") // specifies the table name in the database as "users"

public class User {

    // describes user in our class
    @Id
    // auto-increment the id'
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String email;
    private String username;
    private String password;
    private  String role = "role_user";


}
