package com.nutrieplan.nutrieplan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nutrieplan.nutrieplan.dto.MealRecipeDTO;
import com.nutrieplan.nutrieplan.entity.MealRecipe;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;
import com.nutrieplan.nutrieplan.repositories.MealRecipeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MealService {

    @Autowired
    private MealRecipeRepository mealRecipeRepository;

    public List<MealRecipe> getTypeMealRecipes(UserProfile userProfile, String type) {

        return mealRecipeRepository.findByDailyPlanUserAndMealType(userProfile, type);

    }

    public ResponseEntity<?> replaceMeal(UserProfile userProfile, Long idRecipe, MealRecipeDTO mealDTO) {
        // 1. Busca a receita e verifica se existe
        MealRecipe meal = mealRecipeRepository.findById(idRecipe)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + idRecipe));

        // 2. Verifica se a receita pertence ao usuário
        if (!meal.getDailyPlan().getUser().equals(userProfile)) {
            return ResponseEntity.badRequest().build();
        }

        // 3. Calcula as porcentagens e ajustes
        double mealPercentage = switch (meal.getMealType()) {
            case "Breakfast" -> 0.20;
            case "Lunch" -> 0.38;
            case "Dinner" -> 0.42;
            default -> 0.0;
        };

        Double targetCalories = userProfile.getTdee() * mealPercentage;
        Double yieldConsume = targetCalories / (mealDTO.getCalories() / mealDTO.getYield());

        // 4. Atualiza os dados da receita
        System.out.println("Preparo antigo: " + meal.getPrepareInstructions());

        meal.setUriEdamam(mealDTO.getUriEdamam());
        meal.setImageUrl(mealDTO.getImageUrl());
        meal.setUrlRecipe(mealDTO.getUrlRecipe());
        meal.setCalories(mealDTO.getCalories());
        meal.setCarbohydrate(mealDTO.getCarbohydrate());
        meal.setProtein(mealDTO.getProtein());
        meal.setFat(mealDTO.getFat());
        meal.setFiber(mealDTO.getFiber());
        meal.setConsumeYield(yieldConsume);
        meal.setYield(mealDTO.getYield());
        meal.setPrepareInstructions(mealDTO.getPrepareInstructions());

        System.out.println("Preparo novo: " + meal.getPrepareInstructions());

        // 5. Salva as alterações
        mealRecipeRepository.save(meal);

        return ResponseEntity.ok().build();
    }

}
