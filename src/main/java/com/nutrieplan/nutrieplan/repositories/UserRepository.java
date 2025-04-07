package com.nutrieplan.nutrieplan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutrieplan.nutrieplan.entity.user.User;


public interface UserRepository extends JpaRepository<User, Long> {
 
    User findByEmail(String email);
    
}
