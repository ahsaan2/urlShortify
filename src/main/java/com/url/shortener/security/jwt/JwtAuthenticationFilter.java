package com.url.shortener.security.jwt;

import com.url.shortener.service.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired

    private jwtUtils jwtTokenProvider;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {
        try {
           // Get JWT from header
            String jwt = jwtTokenProvider.getJwtFromHeader(request);
            if (jwt != null && jwtTokenProvider.validateToken(jwt)){
                String username = jwtTokenProvider.getUserNameFromJwtToken(jwt);
                // load user-details
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                         if(userDetails != null){
                          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                          // update security context
                             SecurityContextHolder.getContext().setAuthentication(authentication);
                         }
            }
           // Validate Token
           // If valid get user Details
           // -- get user name -> load user -> set the auth context


        }catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);

    }
    // this filter makes sure that every request has JWT token in it
   // So, for every request, this code will be executed.
}
