package com.nutrieplan.nutrieplan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrieplan.nutrieplan.entity.DailyPlan;
import com.nutrieplan.nutrieplan.services.DailyService;

@RestController
@RequestMapping("/api/plano")
public class DailyController {
    
    @Autowired
    private DailyService dailyService;

    @GetMapping("")
    public DailyPlan getFullDailyPlan(@RequestHeader("Authorization") String token){

        DailyPlan dailyPlan = dailyService.getDailyPlan(token);

        return dailyPlan;
    }
}
