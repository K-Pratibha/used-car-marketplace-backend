package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // Create a bean for a security filter chain, which will be used to configure the security settings for the application.
    // Security configuration properties for the application context that should be used for authentication and authorization
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
           //h(cd)2 formula
        http.csrf().disable().cors().disable();
        //haap formula
        http.authorizeHttpRequests().anyRequest().permitAll();

//        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
//        http.authorizeHttpRequests()
//                .requestMatchers("/api/v1/auth/signup","/api/v1/auth/usersignIn","/api/v1/auth/content-manager-signup","/api/v1/auth/blog-manager-signup","/api/v1/auth/login-otp")
//                .permitAll()
//                //THIS hasRole() method allow only the permitted roles to access
//                .requestMatchers("/api/v1/cars/add-car").hasRole("CONTENT_MANAGER")
//                .anyRequest().authenticated();  // http request permissions for authenticated users

        // permit all requests,for now keep all requests URL open,don't secure them.
        // In a real-world application, you would want to restrict access to certain endpoints based on roles and permissions.
        // You can add more rules to this configuration as per your application requirements.
        // Note: In a production environment, you should consider using a secure method to handle authentication and authorization.
        // For example, you could use JWT (JSON Web Tokens) or OAuth.
        // In this example, we are using a simple form-based login, so we are not handling authentication and authorization here.

        // Finally, build the security filter chain and return it.
        // The securityFilterChain will be used to apply the security settings to incoming requests.
        return http.build();
    }

//    // Create a bean for password encoder approach-1
//    @Bean
//    public PasswordEncoder getPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }


}
