package com.nutrieplan.nutrieplan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nutrieplan.nutrieplan.dto.authentication.AuthenticationDTO;
import com.nutrieplan.nutrieplan.dto.authentication.LoginResponseDTO;
import com.nutrieplan.nutrieplan.dto.authentication.RegisterDTO;
import com.nutrieplan.nutrieplan.entity.ActivityLevel;
import com.nutrieplan.nutrieplan.entity.DietLabel;
import com.nutrieplan.nutrieplan.entity.HealthLabel;
import com.nutrieplan.nutrieplan.entity.user.User;
import com.nutrieplan.nutrieplan.repositories.ActivityLevelRepository;
import com.nutrieplan.nutrieplan.repositories.DietLabelRepository;
import com.nutrieplan.nutrieplan.repositories.HealthLabelRepository;
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

    @Autowired
    private HealthLabelRepository healthLabelRepository;

    @Autowired
    private DietLabelRepository dietLabelRepository;

    @Autowired
    private ActivityLevelRepository activityLevelRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> resgister(@Valid @RequestBody RegisterDTO data) {

        return userService.resgiterUser(data);

    }

    @GetMapping("/check-email")
    public Boolean checkEmailCreate(@RequestParam String email) {

        Boolean existsEmail = userRepository.existsByEmail(email);

        return existsEmail;
    }

    @GetMapping("/resgister-healthlabel")
    public List<HealthLabel> getAllHealthLabel() {

        return healthLabelRepository.findAll();

    }

    @GetMapping("/resgister-dietlabel")
    public List<DietLabel> getAllDietLabel() {

        return dietLabelRepository.findAll();

    }

    @GetMapping("/resgister-activitylevel")
    public List<ActivityLevel> getAllActivityLevel() {

        return activityLevelRepository.findAll();

    }
}
