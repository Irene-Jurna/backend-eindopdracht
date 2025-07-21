package org.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.enums.CuisineType;
import org.example.backend.enums.DishType;
import org.example.backend.enums.HarvestMonth;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipeResponseDto {
    private Long id;
    private String title;
    private String description;
    private List<HarvestMonth> harvestMonth;
    private CuisineType cuisineType;
    private DishType dishType;
    private List<IngredientUsageResponseDto> ingredients;
    private List<String> cookingSteps;
}
