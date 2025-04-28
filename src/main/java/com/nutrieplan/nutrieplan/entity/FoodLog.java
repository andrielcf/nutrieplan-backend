package com.nutrieplan.nutrieplan.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "food_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @ManyToMany
    @JoinTable(name = "foodlog_mealtypes", joinColumns = @JoinColumn(name = "foodlog_id"), inverseJoinColumns = @JoinColumn(name = "mealtype_id"))
    private List<MealType> mealTypes;

    @ManyToMany
    @JoinTable(name = "foodlog_mealrecipes", joinColumns = @JoinColumn(name = "foodlog_id"), inverseJoinColumns = @JoinColumn(name = "mealrecipe_id"))
    private List<MealRecipe> mealRecipes;
}
