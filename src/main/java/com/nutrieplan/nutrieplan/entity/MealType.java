package com.nutrieplan.nutrieplan.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meal_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mealType; // Ex: Lunch, Dinner, Snacks

    @ManyToOne
    @JoinColumn(name = "daily_plan_id")
    private DailyPlan dailyPlan;

    @OneToMany(mappedBy = "mealType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealRecipe> mealRecipes;
}
