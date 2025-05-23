package com.nutrieplan.nutrieplan.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nutrieplan.nutrieplan.entity.DailyPlan;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;

@Service
public class DailyService {

    @Autowired
    private UserService userService;

    public List<DailyPlan> getDailyPlan(String token) {

        UserProfile userProfile = userService.getUserProfileByEmail(token);

        return userProfile.getDailyPlans();
    }


}
