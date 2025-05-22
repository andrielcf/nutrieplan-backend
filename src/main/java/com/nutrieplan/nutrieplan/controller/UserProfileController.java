package com.nutrieplan.nutrieplan.controller;

import com.nutrieplan.nutrieplan.entity.user.UserProfile;
import com.nutrieplan.nutrieplan.entity.ActivityLevel;
import com.nutrieplan.nutrieplan.repositories.UserProfileRepository;

import lombok.Getter;
import lombok.Setter;

import com.nutrieplan.nutrieplan.repositories.ActivityLevelRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/userprofiles")
public class UserProfileController {

    private UserProfileRepository userProfileRepository;
    private ActivityLevelRepository activityLevelRepository;

    public UserProfileController(UserProfileRepository userProfileRepository,
            ActivityLevelRepository activityLevelRepository) {
        this.userProfileRepository = userProfileRepository;
        this.activityLevelRepository = activityLevelRepository;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String id, @RequestBody UpdateUserProfile request) {
        Optional<UserProfile> optionalProfile = userProfileRepository.findById(id);
        if (optionalProfile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserProfile profile = optionalProfile.get();

        if (request.getWeight() != null)
            profile.setWeight(request.getWeight());
        if (request.getHeight() != null)
            profile.setHeight(request.getHeight());
        if (request.getAge() != null)
            profile.setAge(request.getAge());

        if (request.getActivityLevelId() != null) {
            Optional<ActivityLevel> activityLevel = activityLevelRepository.findById(request.getActivityLevelId());
            if (activityLevel.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid activity level ID");
            }
            profile.setActivityLevel(activityLevel.get());
        }

        userProfileRepository.save(profile);
        return ResponseEntity.ok(profile);
    }

    @Getter
    @Setter
    public static class UpdateUserProfile {
        private Double weight;
        private Double height;
        private Integer age;
        private Long activityLevelId;
    }
}
