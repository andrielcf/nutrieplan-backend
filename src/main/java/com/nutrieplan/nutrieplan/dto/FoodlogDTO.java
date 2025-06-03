package com.nutrieplan.nutrieplan.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodlogDTO {

    @NotNull(message = "A data nao pode ser nula")
    private LocalDate localDate;

    @NotNull(message = "O dia da semana nao pode ser nulo")
    private DayOfWeek dayOfWeek;

    @NotBlank(message = "O nome da refeicao e obrigatorio")
    private String name; // Nome da MealType | Personalizado codinome

    @PositiveOrZero(message = "As calorias devem ser zero ou positivas")
    private Double calories;

    @PositiveOrZero(message = "Carboidratos devem ser zero ou positivos")
    private Double carbohydrate;

    @PositiveOrZero(message = "Proteínas devem ser zero ou positivas")
    private Double protein;

    @PositiveOrZero(message = "Gorduras devem ser zero ou positivas")
    private Double fat;

    @PositiveOrZero(message = "Fibras devem ser zero ou positivas")
    private Double fiber;

    @PositiveOrZero(message = "Rendimento deve ser zero ou positivo")
    private Double consumeYield;
}
