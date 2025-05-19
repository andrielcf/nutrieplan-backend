package com.nutrieplan.nutrieplan.entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import com.nutrieplan.nutrieplan.entity.user.UserProfile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "daily_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dia da semana
    private DayOfWeek dayOfWeek;

    // Relação de usuario com dia e suas refeições
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserProfile user;

    @OneToMany(mappedBy = "dailyPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealType> mealTypes;

}
