package com.nutrieplan.nutrieplan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nutrieplan.nutrieplan.dto.UserProfileDTO;
import com.nutrieplan.nutrieplan.dto.authentication.RegisterDTO;
import com.nutrieplan.nutrieplan.entity.ActivityLevel;
import com.nutrieplan.nutrieplan.entity.DietLabel;
import com.nutrieplan.nutrieplan.entity.HealthLabel;
import com.nutrieplan.nutrieplan.entity.user.User;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;
import com.nutrieplan.nutrieplan.entity.user.UserRole;
import com.nutrieplan.nutrieplan.repositories.ActivityLevelRepository;
import com.nutrieplan.nutrieplan.repositories.DietLabelRepository;
import com.nutrieplan.nutrieplan.repositories.HealthLabelRepository;
import com.nutrieplan.nutrieplan.repositories.UserProfileRepository;
import com.nutrieplan.nutrieplan.repositories.UserRepository;
import com.nutrieplan.nutrieplan.security.TokenService;

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

    public ResponseEntity resgiterUser(RegisterDTO data) {

        if (userRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = new User(data.email(), encryptedPassword, UserRole.USER);

        userRepository.save(newUser);

        UserProfileDTO profileDTO = data.profile();

        UserProfile userProfile = new UserProfile();

        userProfile.setUser(newUser);
        userProfile.setName(data.profile().name());
        userProfile.setWeight(data.profile().weight());
        userProfile.setHeight(data.profile().height());
        userProfile.setAge(data.profile().age());

        ActivityLevel activityLevel = activityLevelRepository.findById(profileDTO.activityLevelId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid activity level ID"));

        userProfile.setActivityLevel(activityLevel);

        DietLabel dietLabel = dietLabelRepository.findById(profileDTO.dietLabelId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid diet label ID"));
        userProfile.setDietLabel(dietLabel);

        List<HealthLabel> healthLabels = healthLabelRepository.findAllById(profileDTO.healthLabelsIds());
        userProfile.setHealthLabels(healthLabels);

        userProfileRepository.save(userProfile);

        return ResponseEntity.ok().build();
    }

    // Total daily energy expenditure
    // public double calculateTDEE(UserProfile userProfile){

    // }

}
