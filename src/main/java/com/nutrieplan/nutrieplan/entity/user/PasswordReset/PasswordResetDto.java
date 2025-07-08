package com.nutrieplan.nutrieplan.entity.user.PasswordReset;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetDto {
    private String token;
    private String newPassword;
}
