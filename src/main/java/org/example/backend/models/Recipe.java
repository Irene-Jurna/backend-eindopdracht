package org.example.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.enums.CuisineType;
import org.example.backend.enums.DishType;
import org.example.backend.enums.HarvestMonth;

import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
public class Recipe extends BaseModel {

    // Basis info
    private String title;
    private String description;

    // Enums
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<HarvestMonth> harvestMonth;

    @Enumerated(EnumType.STRING)
    private CuisineType cuisineType;

    @Enumerated(EnumType.STRING)
    private DishType dishType;

    // Relaties
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientUsage> ingredients;

    @ElementCollection
    private List<String> cookingSteps;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    // TODO: nog toevoegen image, createdBy en approved (afhankelijk van User en Image klasses)
}
