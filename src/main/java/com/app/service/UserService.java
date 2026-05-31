package com.app.service;

import com.app.entity.User;
import com.app.payload.LoginDto;
import com.app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    private JWTService jwtService;

    public UserService(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    // methods for user service operations (e.g., create, update, delete, find)
    // interact with user repository to perform database operations.
    public String verifyLogin(
            LoginDto dto
    ){
        // check if user is already present in the repository and update password accordingly
        Optional<User> opUser = userRepository.findByUsername(dto.getUsername());

        if(opUser.isPresent()){
            User user = opUser.get();   //entity object for user service
            //if user password is correct generate token for user service operations
            if(BCrypt.checkpw(dto.getPassword(),user.getPassword())){
               return jwtService.generateToken(user.getUsername());
            }
        }
        return null;  // if user is not present
    }

}

