package com.url.shortener.dtos;

//import lombok.Data;

import lombok.Data;

import java.util.Set;
@Data
public class RegisterRequest {
    private String username;
    private String email;
    private Set<String> role;
    private  String password;
    // user can send these variables. and used


}
/*
DTO defines the structure of the request
* */
