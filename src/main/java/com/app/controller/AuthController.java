package com.app.controller;

import com.app.entity.User;
import com.app.payload.JWTTokenDto;
import com.app.payload.LoginDto;
import com.app.repository.UserRepository;
import com.app.service.JWTService;
import com.app.service.OTPService;
import com.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController             //because this is api
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserRepository userRepository;
    private UserService userService;
    private OTPService otpService;
    private JWTService jwtService;

   //private PasswordEncoder passwordEncoder;    //password encoder for password generation and encrypt the password

     // Constructor injection to inject Us
    public AuthController(UserRepository userRepository, UserService userService, OTPService otpService, JWTService jwtService) {
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.otpService = otpService;
        this.jwtService = jwtService;
    }


    //CRUD methods to inject Auth methods

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(   //we should never return user as string but in JSON format
                                        @RequestBody User user      // User to create the user from the repository
    ){
        // Check if username and email already exists in the database
        // If yes, return an error message
        // If not, save the user in the database and return a success message
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        //if username exist
        if (opUsername.isPresent()){
            return new ResponseEntity<>("Username already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opEmail = userRepository.findByEmailId(user.getEmailId());
        //if email exist
        if (opEmail.isPresent()){
            return new ResponseEntity<>("EmailId already exists",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //approach-1 to encrypt password
        // encode the password before saving the password in the database
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);  //set the encoded password to the user object

        //approach-2
        //BCrypt is used to encrypt the password before saving the password in the database
        //it is build class in the spring security and inside it there is method called hashpw() to encrypt
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashpw);  //set the hashed password to the user object
        //set the role of the user to USER as default role in this application
        user.setRole("ROLE_USER");
        //save the password in the database
        userRepository.save(user);
        return new ResponseEntity<>("user Created",HttpStatus.CREATED);
    }

    //content manager signup method
    @PostMapping("/content-manager-signup")
    public ResponseEntity<?> createContentManagerUser(   //we should never return user as string but in JSON format
                                                         @RequestBody User user      // User to create the user from the repository
    ){
        // Check if username and email already exists in the database
        // If yes, return an error message
        // If not, save the user in the database and return a success message
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        //if username exist
        if (opUsername.isPresent()){
            return new ResponseEntity<>("Username already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opEmail = userRepository.findByEmailId(user.getEmailId());
        //if email exist
        if (opEmail.isPresent()){
            return new ResponseEntity<>("EmailId already exists",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //it is build class in the spring security and inside it there is method called hashpw() to encrypt
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashpw);  //set the hashed password to the user object
        //set the role of the user to USER as default role in this application
        user.setRole("ROLE_CONTENT_MANAGER");
        //save the password in the database
        userRepository.save(user);
        return new ResponseEntity<>("user Created",HttpStatus.CREATED);
    }

    //Blog content signup method
    @PostMapping("/blog-manager-signup")
    public ResponseEntity<?> createBlogManagerUser(   //we should never return user as string but in JSON format
                                                         @RequestBody User user      // User to create the user from the repository
    ){
        // Check if username and email already exists in the database
        // If yes, return an error message
        // If not, save the user in the database and return a success message
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        //if username exist
        if (opUsername.isPresent()){
            return new ResponseEntity<>("Username already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opEmail = userRepository.findByEmailId(user.getEmailId());
        //if email exist
        if (opEmail.isPresent()){
            return new ResponseEntity<>("EmailId already exists",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //it is build class in the spring security and inside it there is method called hashpw() to encrypt
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashpw);  //set the hashed password to the user object
        //set the role of the user to USER as default role in this application
        user.setRole("ROLE_BLOG_MANAGER");
        //save the password in the database
        userRepository.save(user);
        return new ResponseEntity<>("user Created",HttpStatus.CREATED);
    }


    @PostMapping("/usersignIn")
    public ResponseEntity<?> userSignIn(
           @RequestBody LoginDto dto
    ){
        String jwtToken = userService.verifyLogin(dto);
        if (jwtToken!=null){
            JWTTokenDto tokenDto = new JWTTokenDto();
            //set the jwt token and token type to the user
            tokenDto.setToken(jwtToken);
            tokenDto.setTokenType("JWT");
            return new ResponseEntity<>(tokenDto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.INTERNAL_SERVER_ERROR);
    }
//    //dummy url
//    @PostMapping("/message")
//    private String getMessage(){
//        return "hello";
//    }

    @PostMapping("/login-otp")
    public String generateOTP(
            @RequestParam String mobile_number){
        Optional<User> opUser = userRepository.findByMobileNumber(mobile_number);
        if (opUser.isPresent()) {
            String otp = otpService.generateOTP(mobile_number);
            return otp + " " + mobile_number;
        }
        return "User not found";
    }

    //validate the otp
    @PostMapping("/validate-otp")
    public String validateOTP(
            @RequestParam String mobile_number,
            @RequestParam String otp
    ){
        boolean status = otpService.validateOTP(mobile_number, otp);
        if(status) {
            //generate the JWT token
            Optional<User> opUser = userRepository.findByMobileNumber(mobile_number);
            if (opUser.isPresent()) {
                String jwtToken = jwtService.generateToken(opUser.get().getUsername()); //login with username as well as mobile no.
                return jwtToken;
            }
        }
        return status? "OTP validated successfully" : "Invalid OTP";
    }

}
