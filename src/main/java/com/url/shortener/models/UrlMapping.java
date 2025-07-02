package com.url.shortener.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
//import java.util.Date;
import java.util.List;

@Entity
@Data
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String originalUrl;
private String shortUrl;
private  int clickCount =0;
private LocalDateTime localDateTime;

// creating the relationships, with the user(many url mappings can belong to a single user
    @ManyToOne
    @JoinColumn(name = "user_id")  // this specifies foreign key linking of this table
    private  User user;
    @OneToMany(mappedBy = "urlMapping")
    private List<ClickEvent> clickEvents;
}
