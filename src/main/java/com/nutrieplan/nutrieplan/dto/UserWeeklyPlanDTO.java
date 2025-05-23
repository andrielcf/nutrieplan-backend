package com.nutrieplan.nutrieplan.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWeeklyPlanDTO {
    
    @NotNull(message = "Weekly plans cannot be null")
    @Size(min = 6, max = 7, message = "Must provide between 6 and 7 daily plans")
    private List<DailyPlanDTO> weeklyPlans;
}
