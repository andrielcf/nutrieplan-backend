package com.nutrieplan.nutrieplan.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutrieplan.nutrieplan.entity.MealRecipe;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;

public interface MealRecipeRepository extends JpaRepository<MealRecipe, Long> {

    List<MealRecipe> findByDailyPlanUserAndMealType(UserProfile user, String mealType);

    

}
