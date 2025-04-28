package com.nutrieplan.nutrieplan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meal_recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uriEdamam;
    private String imageUrl;
    private String urlRecipe;
    private Double calories;
    private Double fat;
    private Double fiber;
    private String prepareInstructions;

    @ManyToOne
    @JoinColumn(name = "meal_type_id")
    private MealType mealType;
}
