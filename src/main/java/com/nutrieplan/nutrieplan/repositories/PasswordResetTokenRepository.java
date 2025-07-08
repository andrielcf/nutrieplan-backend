package com.nutrieplan.nutrieplan.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutrieplan.nutrieplan.entity.user.User;
import com.nutrieplan.nutrieplan.entity.user.PasswordReset.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    void deleteByUser(User user);

    boolean existsByToken(String token);
}
