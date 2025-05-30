package com.nutrieplan.nutrieplan.dto.authentication;

import java.util.List;

import com.nutrieplan.nutrieplan.dto.DailyPlanDTO;
import com.nutrieplan.nutrieplan.dto.UserProfileDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegisterDTO(

                @NotEmpty(message = "Email: Null or Empty") @Email(message = "Email: Invalid") String email,
                @NotEmpty(message = "Password: Null or Empty") String password,
                UserProfileDTO profile,
                List<@Valid DailyPlanDTO> plans

) {

}
