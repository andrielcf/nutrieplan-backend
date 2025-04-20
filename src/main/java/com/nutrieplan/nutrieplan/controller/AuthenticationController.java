package com.nutrieplan.nutrieplan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrieplan.nutrieplan.dto.authentication.AuthenticationDTO;
import com.nutrieplan.nutrieplan.dto.authentication.LoginResponseDTO;
import com.nutrieplan.nutrieplan.dto.authentication.RegisterDTO;
import com.nutrieplan.nutrieplan.entity.user.User;
import com.nutrieplan.nutrieplan.entity.user.UserRole;
import com.nutrieplan.nutrieplan.repositories.UserRepository;
import com.nutrieplan.nutrieplan.security.TokenService;
import com.nutrieplan.nutrieplan.services.UserService;

import jakarta.validation.Valid;
import lombok.var;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthenticationDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity resgister(@Valid @RequestBody RegisterDTO data) {

        return userService.resgiterUser(data);

    }
}
