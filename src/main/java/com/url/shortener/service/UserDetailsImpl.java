package com.url.shortener.service;

import com.url.shortener.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//import java.io.Serial;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {   // userDetails coming from security package
//   @Serial
    private static final long serialVersionUID = 1;
   private Long id;
   private String username;
   private String email;
   private String password;

    private Collection<?extends GrantedAuthority> authorities;


   // constructor
    public UserDetailsImpl(Long id, String username, String email, String password,Collection<?extends GrantedAuthority> authorities){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    // Build the instance of user details implementation
    // Converts user object from our database into userDetailsImplementation object for spring security
    public  static UserDetailsImpl build(User user){
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return new UserDetailsImpl(
//                user.getRole(),
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(authority)
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
// Acts as bridge for the user and the spring security


}
