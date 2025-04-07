package com.nutrieplan.nutrieplan.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegisterDTO(
    @NotEmpty(message = "Name: Null or Empty") String name,
    @NotEmpty(message = "Email: Null or Empty") @Email(message = "Email: Invalid") String email,
    @NotEmpty(message = "Password: Null or Empty") String password) {
    
}
