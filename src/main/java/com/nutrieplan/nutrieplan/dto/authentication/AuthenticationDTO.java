package com.nutrieplan.nutrieplan.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record AuthenticationDTO(
        @NotEmpty(message = "Email: Null or Empty") @Email(message = "Email: invalid") String email,
        @NotEmpty(message = "Password: Null or Empty") String password) {

}
