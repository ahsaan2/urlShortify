package com.url.shortener.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // JwtAuthenticationFilter will be executed once per request
    @Autowired

    private JwtUtils jwtTokenProvider;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println("Servelet path: "+ request.getServletPath());
        if(path.startsWith("/api/auth/public") || path.matches("^/[^/]+$")){
            System.out.println("Skipping JWT filter for   "+ path);

            filterChain.doFilter(request, response);
            return;
        }
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
            System.out.println("JWT Authentication Failed!");
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);

    }
    // this filter makes sure that every request has JWT token in it
   // So, for every request, this code will be executed.
}
