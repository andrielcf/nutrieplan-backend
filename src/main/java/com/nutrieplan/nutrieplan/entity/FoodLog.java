package com.nutrieplan.nutrieplan.entity;

import java.time.DayOfWeek;
import java.time.LocalDate;

import com.nutrieplan.nutrieplan.entity.user.UserProfile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "food_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate localdate;
    private DayOfWeek dayOfWeek;

    private String name; // Nome da MealType | Personalizado codinome usuario escolhe
    private Double calories;
    private Double carbohydrate;
    private Double protein;
    private Double fat;
    private Double fiber;
    private Double yield;

    // Relação com usuario que fez a refeição
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile user;

}
