package org.example.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.enums.CuisineType;
import org.example.backend.enums.HarvestMonth;

import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
public class Recipe extends BaseModel {
    private String title;
    private String description;
    private List<HarvestMonth> harvestMonth;
    private CuisineType cuisineType;
    // TODO IngredientUsage nog opzetten

}
