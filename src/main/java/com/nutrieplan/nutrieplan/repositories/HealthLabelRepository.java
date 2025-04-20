package com.nutrieplan.nutrieplan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutrieplan.nutrieplan.entity.HealthLabel;

public interface HealthLabelRepository extends JpaRepository<HealthLabel, Long> {

}
