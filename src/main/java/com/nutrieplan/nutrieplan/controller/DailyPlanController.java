package com.nutrieplan.nutrieplan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrieplan.nutrieplan.dto.DailyPlanDTO;
import com.nutrieplan.nutrieplan.entity.DailyPlan;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;
import com.nutrieplan.nutrieplan.services.DailyService;
import com.nutrieplan.nutrieplan.services.UserService;

@RestController
@RequestMapping("/api/dia")
public class DailyPlanController {

    @Autowired
    private DailyService dailyService;

    @Autowired
    private UserService userService;

    @GetMapping("/full")
    public List<DailyPlan> getFullDailyPlan(@RequestHeader("Authorization") String token) {

        List<DailyPlan> dailyPlan = dailyService.getDailyPlan(token);

        return dailyPlan;
    }

    @PutMapping("/rebuildplan")
    public ResponseEntity<?> reBuildDailyPlan(@RequestHeader("Authorization") String token,
            @RequestBody List<DailyPlanDTO> plans) {
        System.out.println("Formando Dias");

        UserProfile userProfile = userService.getUserProfileByEmail(token);

        dailyService.reBuildDailyPlan(userProfile, plans);

        return ResponseEntity.ok().build();
    }
}
