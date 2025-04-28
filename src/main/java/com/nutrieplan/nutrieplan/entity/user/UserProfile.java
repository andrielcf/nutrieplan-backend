package com.nutrieplan.nutrieplan.entity.user;

import java.util.ArrayList;
import java.util.List;

import com.nutrieplan.nutrieplan.entity.ActivityLevel;
import com.nutrieplan.nutrieplan.entity.DailyPlan;
import com.nutrieplan.nutrieplan.entity.DietLabel;
import com.nutrieplan.nutrieplan.entity.Gender;
import com.nutrieplan.nutrieplan.entity.HealthLabel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user_profile")
@Entity(name = "user_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private Double weight; // in kg
    private Double height; // in cm
    private Integer age;

    private Gender gender; // enum: MALE or FEMALE

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyPlan> dailyPlans = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "activity_level_id")
    private ActivityLevel activityLevel;

    @ManyToOne
    @JoinColumn(name = "diet_label_id")
    private DietLabel dietLabel;

    @ManyToMany
    @JoinTable(name = "user_health_labels", joinColumns = @JoinColumn(name = "user_profile_id"), inverseJoinColumns = @JoinColumn(name = "health_label_id"))
    private List<HealthLabel> healthLabels;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
