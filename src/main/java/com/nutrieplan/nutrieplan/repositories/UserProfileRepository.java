package com.nutrieplan.nutrieplan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutrieplan.nutrieplan.entity.user.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

}
