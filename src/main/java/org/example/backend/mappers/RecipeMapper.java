package org.example.backend.mappers;

import org.example.backend.dtos.IngredientUsageResponseDto;
import org.example.backend.dtos.RecipeRequestDto;
import org.example.backend.dtos.RecipeResponseDto;
import org.example.backend.models.IngredientUsage;
import org.example.backend.models.Recipe;

import java.util.List;

public class RecipeMapper {
    public static Recipe toEntity(RecipeRequestDto recipeRequestDto, List<IngredientUsage> ingredientUsages) {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeRequestDto.getTitle());
        recipe.setDescription(recipeRequestDto.getDescription());
        recipe.setHarvestMonth(recipeRequestDto.getHarvestMonth());
        recipe.setCuisineType(recipeRequestDto.getCuisineType());
        recipe.setDishType(recipeRequestDto.getDishType());
        recipe.setIngredients(ingredientUsages);
        recipe.setCookingSteps(recipeRequestDto.getCookingSteps());
        return recipe;
    }

    public static RecipeResponseDto toDto(Recipe recipe) {
        RecipeResponseDto recipeResponseDto = new RecipeResponseDto();
        recipeResponseDto.setId(recipe.getId());
        recipeResponseDto.setTitle(recipe.getTitle());
        recipeResponseDto.setDescription(recipe.getDescription());
        recipeResponseDto.setHarvestMonth(recipe.getHarvestMonth());
        recipeResponseDto.setCuisineType(recipe.getCuisineType());
        recipeResponseDto.setDishType(recipe.getDishType());

        List<IngredientUsageResponseDto> ingredientUsageDtos = recipe
                .getIngredients()
                .stream()
                .map(IngredientUsageMapper::toDto)
                .toList();
        recipeResponseDto.setIngredients(ingredientUsageDtos);

        recipeResponseDto.setCookingSteps(recipe.getCookingSteps());
        return recipeResponseDto;
    }
}
