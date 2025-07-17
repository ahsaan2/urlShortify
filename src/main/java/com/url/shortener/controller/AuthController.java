package com.url.shortener.controller;

import com.url.shortener.dtos.LoginRequest;
import com.url.shortener.dtos.RegisterRequest;
import com.url.shortener.models.User;
import com.url.shortener.repository.UserRepository;
import com.url.shortener.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController   // gives the controller + response body
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    // LOGIN A USER
    @PostMapping("/public/login")
    public  ResponseEntity<?> loginUser (@RequestBody LoginRequest loginRequest){
        try {

            return ResponseEntity.ok(userService.authenticateUser(loginRequest));
        }
        catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password!");
        }
    }



    // REGISTER A USER
    @PostMapping("/public/register")  // api/auth/...
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
    // what all information you should send over here  -->> registerRequest dto is automatically populated here
       User user = new User();

      user.setUsername(registerRequest.getUsername());
      user.setPassword(registerRequest.getPassword());
      user.setEmail(registerRequest.getEmail());
      user.setRole("Hardcoded value");
      userService.registerUser(user);
//      return ResponseEntity.ok(user);
      return  ResponseEntity.ok("user registered Successfully!");

    }
}
