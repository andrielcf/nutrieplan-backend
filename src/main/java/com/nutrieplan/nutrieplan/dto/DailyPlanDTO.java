package com.nutrieplan.nutrieplan.dto;

import java.time.DayOfWeek;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DailyPlanDTO {
    
    @NotNull(message = "Day of week cannot be null")
    private DayOfWeek dayOfWeek;
    
    @NotNull(message = "Meals list cannot be null")
    @Size(min = 3, message = "At least three meal must be provided")
    private List<@Valid MealRecipeDTO> meals;
}
