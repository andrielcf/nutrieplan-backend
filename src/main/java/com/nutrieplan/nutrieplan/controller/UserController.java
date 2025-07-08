package com.nutrieplan.nutrieplan.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.nutrieplan.nutrieplan.dto.MealPlannerReponse;
import com.nutrieplan.nutrieplan.dto.UserProfileDTO;
import com.nutrieplan.nutrieplan.entity.user.User;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;
import com.nutrieplan.nutrieplan.security.TokenService;
import com.nutrieplan.nutrieplan.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    // Testing GET's
    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Testing GET's
    @GetMapping("/gettoken")
    public String getToken(@RequestHeader("Authorization") String token) {

        String tokenExtract = tokenService.extractSubject(token);

        return tokenExtract;

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userService.getUserById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/details")
    public String getHealthLabels(@RequestHeader("Authorization") String token) {

        String tokenExtract = tokenService.extractSubject(token);

        return userService.getHealt(tokenExtract);
    }

    @GetMapping("/tdee")
    public Double getTdee(@RequestHeader("Authorization") String token) {

        UserProfile userProfile = userService.getUserProfileByEmail(token);

        Double tdee = userProfile.getTdee();

        return tdee;
    }

    @GetMapping("/meal-details")
    public ResponseEntity<MealPlannerReponse> getMealDetails(@RequestHeader("Authorization") String token){

        
        MealPlannerReponse mealPlannerReponse = userService.getMealPlannerDetails(userService.getUserProfileByEmail(token));

        return ResponseEntity.ok(mealPlannerReponse);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> userProfileUpdate(@RequestHeader("Authorization") String token, @RequestBody UserProfileDTO userProfileDTO){

        UserProfile userProfile = userService.getUserProfileByEmail(token);

        userService.userProfileUpdate(userProfile, userProfileDTO);
    
        return ResponseEntity.ok().build();
    }

    @GetMapping("/consult-userprofile")
    public UserProfile getUserProfileConfig(@RequestHeader("Authorization") String token){

        UserProfile userProfile = userService.getUserProfileByEmail(token);

        UserProfile userProfileResponse = new UserProfile();

        userProfileResponse.setName(userProfile.getName());
        userProfileResponse.setAge(userProfile.getAge());
        userProfileResponse.setDietLabel(userProfile.getDietLabel());
        userProfileResponse.setHealthLabels(userProfile.getHealthLabels());
        userProfileResponse.setGender(userProfile.getGender());
        userProfileResponse.setHeight(userProfile.getHeight());
        userProfileResponse.setWeight(userProfile.getWeight());
        userProfileResponse.setActivityLevel(userProfile.getActivityLevel());
        userProfileResponse.setTdee(userProfile.getTdee());

        return userProfileResponse;
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token){

        UserProfile userProfile = userService.getUserProfileByEmail(token);
        
        userService.deleteUser(userProfile);

        return ResponseEntity.ok().build();
    }

}