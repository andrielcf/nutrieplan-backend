package com.nutrieplan.nutrieplan.services;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nutrieplan.nutrieplan.entity.DailyPlan;
import com.nutrieplan.nutrieplan.entity.MealRecipe;
import com.nutrieplan.nutrieplan.entity.MealType;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;
import com.nutrieplan.nutrieplan.repositories.UserProfileRepository;

@Service
public class DailyService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public List<DailyPlan> getDailyPlan(String token) {

        UserProfile userProfile = userService.getUserProfileByEmail(token);

        // userProfile.setDailyPlans(randomPlan(userProfile));

        // userProfileRepository.save(userProfile);
        System.out.println("1");
        userProfile.getDailyPlans().clear();
        System.out.println("2");
        userProfile.getDailyPlans().addAll(randomPlan(userProfile));
        System.out.println("3");
        userProfileRepository.save(userProfile);

        return userProfile.getDailyPlans();
    }

    public List<DailyPlan> randomPlan(UserProfile userProfile) {

        List<DailyPlan> plans = new ArrayList<>();

        List<String> mealTypeNames = List.of("Breakfast", "Lunch", "Dinner");

        // Para cada dia da semana
        for (DayOfWeek day : DayOfWeek.values()) {
            DailyPlan plan = new DailyPlan();
            plan.setDayOfWeek(day);
            plan.setUser(userProfile);

            List<MealType> mealTypes = new ArrayList<>();

            Integer count = 0;

            MealType mealType = new MealType();
            for (String typeName : mealTypeNames) {

                count += 1;
                mealType.setMealType(typeName);
                mealType.setDailyPlan(plan);

                // Criando uma receita fictícia
                MealRecipe recipe = new MealRecipe();
                recipe.setUriEdamam("fake-uri-");
                recipe.setImageUrl("https://example.com/image.jpg"+ count);
                recipe.setUrlRecipe("https://example.com/recipe");
                recipe.setCalories(500.0);
                recipe.setCarbohydrate(60.0);
                recipe.setProtein(20.0);
                recipe.setFat(15.0);
                recipe.setFiber(5.0);
                recipe.setYield(1.0);
                recipe.setPrepareInstructions("Mix ingredients and cook."+ count);

                recipe.setMealType(mealType);

                mealType.setMealRecipes(List.of(recipe));

                mealTypes.add(mealType);
            }

            plan.setMealTypes(mealTypes);
            plans.add(plan);
        }

        return plans;
    }

}
