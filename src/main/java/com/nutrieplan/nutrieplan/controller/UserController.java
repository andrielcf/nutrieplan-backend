package com.nutrieplan.nutrieplan.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrieplan.nutrieplan.entity.user.User;
import com.nutrieplan.nutrieplan.security.TokenService;
import com.nutrieplan.nutrieplan.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;


    // Testing GET's
    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();

        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Testing GET's
    @GetMapping("/gettoken")
    public String getToken(@RequestHeader("Authorization") String token){
        
        String tokenExtract = tokenService.extractSubject(token);

        return tokenExtract;

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> optionalUser = userService.getUserById(id);

        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}