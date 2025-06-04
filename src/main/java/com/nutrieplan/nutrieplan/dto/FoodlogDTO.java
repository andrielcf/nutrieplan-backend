package com.nutrieplan.nutrieplan.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodlogDTO {

    @NotNull(message = "A data não pode ser nula")
    private LocalDate localDate;

    @NotNull(message = "O dia da semana não pode ser nulo")
    private DayOfWeek dayOfWeek;

    @NotBlank(message = "O nome da refeição é obrigatório")
    @Size(max = 255, message = "O nome da refeição deve ter no máximo 255 caracteres")
    private String name;

    @NotNull(message = "As calorias não podem ser nulas")
    @PositiveOrZero(message = "As calorias devem ser zero ou positivas")
    private Double calories;

    @NotNull(message = "Carboidratos não podem ser nulos")
    @PositiveOrZero(message = "Carboidratos devem ser zero ou positivos")
    private Double carbohydrate;

    @NotNull(message = "Proteínas não podem ser nulas")
    @PositiveOrZero(message = "Proteínas devem ser zero ou positivas")
    private Double protein;

    @NotNull(message = "Gorduras não podem ser nulas")
    @PositiveOrZero(message = "Gorduras devem ser zero ou positivas")
    private Double fat;

    @NotNull(message = "Fibras não podem ser nulas")
    @PositiveOrZero(message = "Fibras devem ser zero ou positivas")
    private Double fiber;

    @NotNull(message = "Rendimento não pode ser nulo")
    @PositiveOrZero(message = "Rendimento deve ser zero ou positivo")
    private Double consumeYield;
}
