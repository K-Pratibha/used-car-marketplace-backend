package com.app.repository;

import com.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //finder method
    Optional<User> findByUsername(String username);     //checking if username exists or not? if does then don't sign in
    Optional<User> findByEmailId(String email);     //checking if email exists or not? if does then don't sign in
    Optional<User> findByMobileNumber(String mobileNumber); //checking if mobile number exists or not? if does then don't sign in'

}