package com.nutrieplan.nutrieplan.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nutrieplan.nutrieplan.dto.FoodlogDTO;
import com.nutrieplan.nutrieplan.entity.FoodLog;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;
import com.nutrieplan.nutrieplan.services.FoodLogService;
import com.nutrieplan.nutrieplan.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/foodlog")
public class FoodLogController {

    @Autowired
    private UserService userService;

    @Autowired
    private FoodLogService foodLogService;

    @PostMapping("/consume")
    public ResponseEntity<?> consumeRecipe(@RequestHeader("Authorization") String token,
            @RequestBody @Valid FoodlogDTO foodlogDTO) {

        UserProfile userProfile = userService.getUserProfileByEmail(token);

        foodLogService.consumeRecipe(userProfile, foodlogDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/selected-date")
    public List<FoodLog> getSelectedDate(@RequestHeader("Authorization") String token, @RequestParam LocalDate date) {

        UserProfile userProfile = userService.getUserProfileByEmail(token);
        List<FoodLog> foodLogs = foodLogService.getSelectedDate(userProfile, date);

        return foodLogs;
    }

    @DeleteMapping("/meal-remove")
    public ResponseEntity<?> removeConsumeMeal(@RequestHeader("Authorization") String token, @RequestParam Long id){

        UserProfile userProfile = userService.getUserProfileByEmail(token);
        
        return foodLogService.deleteConsumeMeal(userProfile, id);
    }
}
