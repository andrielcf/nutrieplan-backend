package com.nutrieplan.nutrieplan.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrieplan.nutrieplan.entity.user.User;
import com.nutrieplan.nutrieplan.entity.user.PasswordReset.PasswordResetDto;
import com.nutrieplan.nutrieplan.entity.user.PasswordReset.PasswordResetRequestDto;
import com.nutrieplan.nutrieplan.repositories.UserRepository;
import com.nutrieplan.nutrieplan.services.PasswordResetTokenService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    @Autowired
    private PasswordResetTokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetRequestDto request) throws MessagingException {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("E-mail não encontrado");
        }

        tokenService.createPasswordResetTokenForUser(user);

        return ResponseEntity.ok("E-mail de recuperação enviado");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
        String result = tokenService.validatePasswordResetToken(passwordResetDto.getToken());

        if (result != null) {
            return ResponseEntity.badRequest().body(result);
        }

        User user = tokenService.getUserByPasswordResetToken(passwordResetDto.getToken());
        if (user != null) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(passwordResetDto.getNewPassword());
            System.out.println("Senha antiga: " + user.getPassword());
            
            user.setPassword(encryptedPassword);
            System.out.println("Senha nova: " + user.getPassword());

            userRepository.save(user);
            return ResponseEntity.ok("Senha alterada com sucesso");
        } else {
            return ResponseEntity.badRequest().body("Token inválido");
        }
    }

}
