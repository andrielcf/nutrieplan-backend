package com.nutrieplan.nutrieplan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nutrieplan.nutrieplan.dto.MealRecipeDTO;
import com.nutrieplan.nutrieplan.entity.MealRecipe;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;
import com.nutrieplan.nutrieplan.services.MealService;
import com.nutrieplan.nutrieplan.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mealrecipe")
public class MealRecipeController {

    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;

    @GetMapping("/meals/{mealType}")
    public List<MealRecipe> getTypeMealRecipe(@RequestHeader("Authorization") String token,
            @PathVariable String mealType) {

        UserProfile userProfile = userService.getUserProfileByEmail(token);

        System.out.println(mealType);

        List<MealRecipe> mealRecipes = mealService.getTypeMealRecipes(userProfile, mealType);

        return mealRecipes;
    }

    @PutMapping("/replace-meal/{idRecipe}")
    public ResponseEntity<?> replaceMeal(@RequestHeader("Authorization") String token, @PathVariable Long idRecipe,
            @RequestBody MealRecipeDTO mealRecipeDTO) {

        UserProfile userProfile = userService.getUserProfileByEmail(token);

        mealService.replaceMeal(userProfile, idRecipe, mealRecipeDTO);

        return ResponseEntity.ok().build();
    }
}
