package com.nutrieplan.nutrieplan.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nutrieplan.nutrieplan.dto.DailyPlanDTO;
import com.nutrieplan.nutrieplan.dto.MealRecipeDTO;
import com.nutrieplan.nutrieplan.dto.UserProfileDTO;
import com.nutrieplan.nutrieplan.dto.authentication.RegisterDTO;
import com.nutrieplan.nutrieplan.entity.ActivityLevel;
import com.nutrieplan.nutrieplan.entity.DailyPlan;
import com.nutrieplan.nutrieplan.entity.DietLabel;
import com.nutrieplan.nutrieplan.entity.Gender;
import com.nutrieplan.nutrieplan.entity.HealthLabel;
import com.nutrieplan.nutrieplan.entity.MealRecipe;
import com.nutrieplan.nutrieplan.entity.user.User;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;
import com.nutrieplan.nutrieplan.entity.user.UserRole;
import com.nutrieplan.nutrieplan.repositories.ActivityLevelRepository;
import com.nutrieplan.nutrieplan.repositories.DietLabelRepository;
import com.nutrieplan.nutrieplan.repositories.HealthLabelRepository;
import com.nutrieplan.nutrieplan.repositories.UserProfileRepository;
import com.nutrieplan.nutrieplan.repositories.UserRepository;
import com.nutrieplan.nutrieplan.security.TokenService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ActivityLevelRepository activityLevelRepository;

    @Autowired
    private DietLabelRepository dietLabelRepository;

    @Autowired
    private HealthLabelRepository healthLabelRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public ResponseEntity<?> resgiterUser(@Valid RegisterDTO data) {

        if (userRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = new User(data.email(), encryptedPassword, UserRole.USER);

        UserProfileDTO profileDTO = data.profile();

        UserProfile userProfile = new UserProfile();

        userProfile.setUser(newUser);
        userProfile.setName(data.profile().name());
        userProfile.setWeight(data.profile().weight());
        userProfile.setHeight(data.profile().height());
        userProfile.setGender(data.profile().gender());
        userProfile.setAge(data.profile().age());

        ActivityLevel activityLevel = activityLevelRepository.findById(profileDTO.activityLevelId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid activity level ID"));

        userProfile.setActivityLevel(activityLevel);

        userProfile.setTdee(calculateTDEE(userProfile));

        DietLabel dietLabel = dietLabelRepository.findById(profileDTO.dietLabelId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid diet label ID"));
        userProfile.setDietLabel(dietLabel);

        List<HealthLabel> healthLabels = healthLabelRepository.findAllById(profileDTO.healthLabelsIds());
        userProfile.setHealthLabels(healthLabels);

        // Monta os planos diários e refeições
        List<DailyPlanDTO> dailyPlanDTOs = data.plans();
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

                    System.out.println("Set to Launch and convert to False");

                    convertDinner = false;
                    meal.setMealType("Lunch");

                    System.out.println(mealDTO.getMealType());
                } else if ("Lunch/dinner".equals(mealDTO.getMealType()) && !convertDinner) {
                    System.out.println("Set to Dinner and convert to True");

                    convertDinner = true;
                    meal.setMealType("Dinner");
                    System.out.println(mealDTO.getMealType());
                } else {

                    meal.setMealType(mealDTO.getMealType());
                }
                meal.setUriEdamam(mealDTO.getUriEdamam());
                meal.setImageUrl(mealDTO.getImageUrl());
                meal.setUrlRecipe(mealDTO.getUrlRecipe());
                meal.setCalories(mealDTO.getCalories());
                meal.setCarbohydrate(mealDTO.getCarbohydrate());
                meal.setProtein(mealDTO.getProtein());
                meal.setFat(mealDTO.getFat());
                meal.setFiber(mealDTO.getFiber());
                meal.setYield(mealDTO.getYield());
                meal.setPrepareInstructions(mealDTO.getPrepareInstructions());
                meal.setDailyPlan(dailyPlan);
                meals.add(meal);
            }

            dailyPlan.setMealRecipes(meals);
            dailyPlans.add(dailyPlan);
        }

        userProfile.setDailyPlans(dailyPlans);

        // SAVE USER
        userRepository.save(newUser);
        userProfileRepository.save(userProfile);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> reBuildDailyPlan(List<DailyPlanDTO> planDTO, UserProfile userProfile) {

        // 1. Remove planos antigos (se necessário)
        if (userProfile.getDailyPlans() != null && !userProfile.getDailyPlans().isEmpty()) {
            userProfile.getDailyPlans().clear();
        }

        // Monta os planos diários e refeições
        List<DailyPlanDTO> dailyPlanDTOs = planDTO;
        List<DailyPlan> dailyPlans = new ArrayList<>();

        for (DailyPlanDTO dailyPlanDTO : dailyPlanDTOs) {
            DailyPlan dailyPlan = new DailyPlan();
            dailyPlan.setDayOfWeek(dailyPlanDTO.getDayOfWeek());
            dailyPlan.setUser(userProfile);

            List<MealRecipe> meals = new ArrayList<>();
            for (MealRecipeDTO mealDTO : dailyPlanDTO.getMeals()) {
                MealRecipe meal = new MealRecipe();
                meal.setMealType(mealDTO.getMealType());
                meal.setUriEdamam(mealDTO.getUriEdamam());
                meal.setImageUrl(mealDTO.getImageUrl());
                meal.setUrlRecipe(mealDTO.getUrlRecipe());
                meal.setCalories(mealDTO.getCalories());
                meal.setCarbohydrate(mealDTO.getCarbohydrate());
                meal.setProtein(mealDTO.getProtein());
                meal.setFat(mealDTO.getFat());
                meal.setFiber(mealDTO.getFiber());
                meal.setYield(mealDTO.getYield());
                meal.setPrepareInstructions(mealDTO.getPrepareInstructions());
                meal.setDailyPlan(dailyPlan);

                meals.add(meal);
            }

            dailyPlan.setMealRecipes(meals);
            dailyPlans.add(dailyPlan);
        }

        return ResponseEntity.ok().build();
    }

    public double calculateTDEE(UserProfile userProfile) {
        double tmb;

        if (userProfile.getGender() == Gender.MALE) {
            tmb = (10 * userProfile.getWeight()) + (6.25 * userProfile.getHeight())
                    - (5 * userProfile.getAge()) + 5;
        } else {
            tmb = (10 * userProfile.getWeight()) + (6.25 * userProfile.getHeight())
                    - (5 * userProfile.getAge()) - 161;
        }

        ActivityLevel activityLevel = userProfile.getActivityLevel();
        if (activityLevel == null) {
            throw new IllegalArgumentException("Nível de atividade não definido para o usuário.");
        }

        double tdee = tmb * activityLevel.getFactor();

        return Math.floor(tdee);

    }

    public String getHealt(String token) {

        User user = userRepository.findByEmail(token);

        UserProfile userProfile = user.getUserProfile();

        System.out.println(userProfile.getHealthLabels());

        List<HealthLabel> healthLabels = userProfile.getHealthLabels();

        List<String> healthLablesList = new ArrayList<>();

        for (HealthLabel healthLabel : healthLabels) {
            System.out.println(healthLabel.getName());
            healthLablesList.add(healthLabel.getName());
        }
        System.out.println(healthLablesList);

        return user.getEmail();
    }

    public UserProfile getUserProfileByEmail(String token) {

        String email = tokenService.extractSubject(token);
        User user = userRepository.findByEmail(email);
        if (user != null && user.getUserProfile() != null) {
            return user.getUserProfile();
        }
        throw new EntityNotFoundException("Perfil de usuário não encontrado para o e-mail: " + email);
    }

}
