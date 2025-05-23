package com.nutrieplan.nutrieplan.dto;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MealRecipeDTO {
    @NotBlank(message = "Meal type cannot be blank")
    private String mealType; // café da manhã, almoço, etc.
    
    @NotBlank(message = "Edamam URI cannot be blank")
    private String uriEdamam;
    
    @NotBlank(message = "Image URL cannot be blank")
    @URL(message = "Image URL must be a valid URL")
    private String imageUrl;
    
    @NotBlank(message = "Recipe URL cannot be blank")
    @URL(message = "Recipe URL must be a valid URL")
    private String urlRecipe;
    
    @NotNull(message = "Calories cannot be null")
    @PositiveOrZero(message = "Calories must be positive or zero")
    private Double calories;
    
    @NotNull(message = "Carbohydrate cannot be null")
    @PositiveOrZero(message = "Carbohydrate must be positive or zero")
    private Double carbohydrate;
    
    @NotNull(message = "Protein cannot be null")
    @PositiveOrZero(message = "Protein must be positive or zero")
    private Double protein;
    
    @NotNull(message = "Fat cannot be null")
    @PositiveOrZero(message = "Fat must be positive or zero")
    private Double fat;
    
    @NotNull(message = "Fiber cannot be null")
    @PositiveOrZero(message = "Fiber must be positive or zero")
    private Double fiber;
    
    @NotNull(message = "Yield cannot be null")
    @Positive(message = "Yield must be positive")
    private Double yield;
    
    @NotBlank(message = "Prepare instructions cannot be blank")
    private String prepareInstructions;
    
    // getters e setters
}
