package com.nutrieplan.nutrieplan.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nutrieplan.nutrieplan.dto.DailyPlanDTO;
import com.nutrieplan.nutrieplan.dto.MealRecipeDTO;
import com.nutrieplan.nutrieplan.entity.DailyPlan;
import com.nutrieplan.nutrieplan.entity.MealRecipe;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;
import com.nutrieplan.nutrieplan.repositories.UserProfileRepository;
import com.nutrieplan.nutrieplan.repositories.UserRepository;

import jakarta.validation.Valid;

@Service
public class DailyService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public List<DailyPlan> getDailyPlan(String token) {

        UserProfile userProfile = userService.getUserProfileByEmail(token);

        return userProfile.getDailyPlans();
    }

    public ResponseEntity<?> reBuildDailyPlan(UserProfile userProfile, List<@Valid DailyPlanDTO> plans) {

        /// 1. Limpa os planos existentes de forma segura
        if (userProfile.getDailyPlans() == null) {
            userProfile.setDailyPlans(new ArrayList<>());
        } else {
            userProfile.getDailyPlans().clear(); // Isso já é suficiente
        }

        // Monta os planos diários e refeições
        List<DailyPlanDTO> dailyPlanDTOs = plans;
        List<DailyPlan> dailyPlans = new ArrayList<>();

        Boolean convertDinner = true;

        for (DailyPlanDTO dailyPlanDTO : dailyPlanDTOs) {
            DailyPlan dailyPlan = new DailyPlan();
            dailyPlan.setDayOfWeek(dailyPlanDTO.getDayOfWeek());
            dailyPlan.setUser(userProfile);

            List<MealRecipe> meals = new ArrayList<>();
            for (MealRecipeDTO mealDTO : dailyPlanDTO.getMeals()) {
                MealRecipe meal = new MealRecipe();

                if ("Lunch/dinner".equals(mealDTO.getMealType()) && convertDinner) {

                    convertDinner = false;
                    meal.setMealType("Lunch");
                } else if ("Lunch/dinner".equals(mealDTO.getMealType()) && !convertDinner) {

                    convertDinner = true;
                    meal.setMealType("Dinner");
                } else {

                    meal.setMealType(mealDTO.getMealType());
                }

                double mealPercentage = switch (meal.getMealType()) {
                    case "Breakfast" -> 0.20;
                    case "Lunch" -> 0.38;
                    case "Dinner" -> 0.42;
                    default -> 0.0;
                };

                Double targetCalories = userProfile.getTdee() * mealPercentage;
                System.out.println("/////////////////////////////////////////////");
                System.out.println("Voce vai consumir " + mealPercentage + "% de Kcal no" + meal.getMealType() + ": "
                        + targetCalories);

                Double yieldConsume = targetCalories / (mealDTO.getCalories() / mealDTO.getYield());
                System.out
                        .println("| Refeição de " + meal.getMealType() + "\n| Total de Kacl: " + mealDTO.getCalories());
                System.out.println("| Total de Kacl por porção: " + mealDTO.getCalories() / mealDTO.getYield());
                System.out.println("| Total de porção original: " + mealDTO.getYield());
                System.out.println("| Calculo de KCAL no " + meal.getMealType() + ": "
                        + (mealDTO.getCalories() / mealDTO.getYield()) * yieldConsume);

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
                meal.setDailyPlan(dailyPlan);
                meals.add(meal);
            }

            dailyPlan.setMealRecipes(meals);
            dailyPlans.add(dailyPlan);
        }

        userProfile.getDailyPlans().addAll(dailyPlans);

        userProfileRepository.save(userProfile);
        return ResponseEntity.ok().build();
    }

}
