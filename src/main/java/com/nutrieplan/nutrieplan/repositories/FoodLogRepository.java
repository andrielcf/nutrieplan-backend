package com.nutrieplan.nutrieplan.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutrieplan.nutrieplan.entity.FoodLog;
import com.nutrieplan.nutrieplan.entity.user.UserProfile;

public interface FoodLogRepository extends JpaRepository<FoodLog, Long> {
    
    List<FoodLog> findByUserProfileAndDate(UserProfile user, LocalDate localdate);

    Optional<FoodLog> findByUserProfileAndId(UserProfile user, Long id);
    
    void deleteByUserProfileAndId(UserProfile user, Long id);
}
