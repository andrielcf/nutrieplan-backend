package com.nutrieplan.nutrieplan.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nutrieplan.nutrieplan.entity.ActivityLevel;
import com.nutrieplan.nutrieplan.entity.DietLabel;
import com.nutrieplan.nutrieplan.entity.HealthLabel;
import com.nutrieplan.nutrieplan.repositories.ActivityLevelRepository;
import com.nutrieplan.nutrieplan.repositories.DietLabelRepository;
import com.nutrieplan.nutrieplan.repositories.HealthLabelRepository;

import jakarta.annotation.PostConstruct;

@Component
public class LabelSeeder {

    @Autowired
    private ActivityLevelRepository activityLevelRepo;

    @Autowired
    private DietLabelRepository dietLabelRepo;

    @Autowired
    private HealthLabelRepository healthLabelRepo;

    @PostConstruct
    public void seed() {
        if (activityLevelRepo.count() == 0) {
            activityLevelRepo.saveAll(List.of(
                    // new ActivityLevel(null, "SEDENTARY", 1.2),
                    // new ActivityLevel(null, "LIGHT", 1.375),
                    // new ActivityLevel(null, "MODERATE", 1.55),
                    // new ActivityLevel(null, "ACTIVE", 1.725),
                    // new ActivityLevel(null, "VERY_ACTIVE", 1.9)));
                    new ActivityLevel(null, "Sedentário (pouco ou nenhum exercício)", 1.2),
                    new ActivityLevel(null, "Levemente ativo (exercício 1-3 vezes/semana)", 1.375),
                    new ActivityLevel(null, "Moderadamente ativo (exercício 3-5 vezes/semana)", 1.55),
                    new ActivityLevel(null, "Muito ativo (exercício 6-7 vezes/semana)", 1.725),
                    new ActivityLevel(null, "Extremamente ativo (exercício intenso diário)", 1.9)));
        }

        if (dietLabelRepo.count() == 0) {
            dietLabelRepo.saveAll(List.of(
                    new DietLabel(null, "balanced", "Balanciada"),
                    new DietLabel(null, "high-protein", "Alto em Proteina"),
                    new DietLabel(null, "low-fat", "Baixo em Gordura"),
                    new DietLabel(null, "low-carb", "Baixo em Carboidrato")));
        }

        if (healthLabelRepo.count() == 0) {
            healthLabelRepo.saveAll(List.of(
                    new HealthLabel(null, "vegan", "Vegana"),
                    new HealthLabel(null, "vegetarian", "Vegetariana"),
                    new HealthLabel(null, "gluten-free", "Sem glúten"),
                    new HealthLabel(null, "dairy-free", "Sem lactose")
                    ));
        }
    }

}
