package org.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.CuisineType;
import org.example.backend.enums.DishType;
import org.example.backend.enums.HarvestMonth;

import java.util.List;

@Getter
@Setter
public class RecipeRequestDto {
    private String title;
    private String description;
    private List<HarvestMonth> harvestMonth;
    private CuisineType cuisineType;
    private DishType dishType;
    private List<IngredientUsageRequestDto> ingredients;
    private List<String> cookingSteps;
}
