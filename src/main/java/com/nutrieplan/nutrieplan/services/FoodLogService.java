package com.nutrieplan.nutrieplan.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nutrieplan.nutrieplan.dto.FoodlogDTO;
import com.nutrieplan.nutrieplan.entity.FoodLog;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;
import com.nutrieplan.nutrieplan.repositories.FoodLogRepository;

@Service
public class FoodLogService {

    @Autowired
    private FoodLogRepository foodLogRepository;

    public ResponseEntity<?> consumeRecipe(UserProfile userProfile, FoodlogDTO foodlogDTO) {

        FoodLog foodLog = new FoodLog(null, foodlogDTO.getLocalDate(), foodlogDTO.getDayOfWeek(), foodlogDTO.getName(),
                foodlogDTO.getCalories(), foodlogDTO.getCarbohydrate(), foodlogDTO.getProtein(), foodlogDTO.getFat(),
                foodlogDTO.getFiber(), foodlogDTO.getConsumeYield(), userProfile);

        foodLogRepository.save(foodLog);

        return ResponseEntity.ok().build();
    }

    public List<FoodLog> getSelectedDate(UserProfile userProfile, LocalDate date) {

        List<FoodLog> foodLogs = foodLogRepository.findByUserProfileAndDate(userProfile, date);

        return foodLogs;
    }

    public ResponseEntity<?> deleteConsumeMeal(UserProfile userProfile, Long id) {

        if (foodLogRepository.findByUserProfileAndId(userProfile, id).isPresent()) {

            foodLogRepository.deleteById(id);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

}
