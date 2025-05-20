package com.nutrieplan.nutrieplan.dto;

import java.util.List;

import com.nutrieplan.nutrieplan.entity.Gender;
import jakarta.validation.constraints.NotEmpty;

public record UserProfileDTO(
                @NotEmpty(message = "Name: Null or Empty") String name,
                @NotEmpty(message = "Weight: Null or Empty") Double weight,
                @NotEmpty(message = "Height: Null or Empty") Double height,
                @NotEmpty(message = "Age: Null or Empty") Integer age,
                @NotEmpty(message = "Gender: Null or Empty") Gender gender,
                @NotEmpty(message = "ActivityLevel: Null or Empty") Long activityLevelId,
                @NotEmpty(message = "DietLabel: Null or Empty") Long dietLabelId,
                @NotEmpty(message = "HealthLabel: Null or Empty") List<Long> healthLabelsIds

) {

}
