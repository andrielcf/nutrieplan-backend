package com.nutrieplan.nutrieplan.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MealPlannerReponse {
    
    Double tdee;

    String dietLabels;

    List<String> healthLabels;
}
