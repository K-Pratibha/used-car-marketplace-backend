package com.app.config;

import com.app.entity.User;
import com.app.repository.UserRepository;
import com.app.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private UserRepository userRepository;

    public JWTFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, //it contains the http request which has authorization header and request
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token!=null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(8,token.length()-1);
            String username = jwtService.getUsername(jwtToken); //in service layer supplying the token and getting username
            Optional<User> opUser = userRepository.findByUsername(username);
            if (opUser.isPresent()) {
                User user = opUser.get();

                // If the user is already authenticated and the user is not authenticated then create a new user and add it to the list of users
                UsernamePasswordAuthenticationToken
                        authenticationToken = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                        Collections.singleton(new SimpleGrantedAuthority(user.getRole()))   //user details and role details are then it ll verify the config file
                        );
                // Set the authentication details to the authentication token
                authenticationToken.setDetails
                        (new WebAuthenticationDetails(request));
                //now spring security have username and password
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
        filterChain.doFilter(request, response);
    }
}
