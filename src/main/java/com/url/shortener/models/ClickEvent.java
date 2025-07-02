package com.url.shortener.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
//import java.util.Date;
@Entity
@Data
public class ClickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime clickDate;
    @ManyToOne  // this is a many to one relationship, with url mapping
    @JoinColumn(name = "url_mapping_id")
    private UrlMapping urlMapping;
}
