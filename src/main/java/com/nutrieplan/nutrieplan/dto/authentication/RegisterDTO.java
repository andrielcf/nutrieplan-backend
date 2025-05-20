package com.nutrieplan.nutrieplan.dto.authentication;

import com.nutrieplan.nutrieplan.dto.UserProfileDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegisterDTO(

                @NotEmpty(message = "Email: Null or Empty") @Email(message = "Email: Invalid") String email,
                @NotEmpty(message = "Password: Null or Empty") String password,
                UserProfileDTO profile

) {

}
