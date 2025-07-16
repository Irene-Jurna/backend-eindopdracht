package org.example.backend.services;

import org.example.backend.dtos.RecipeRequestDto;
import org.example.backend.models.Recipe;
import org.example.backend.repositories.IngredientUsageRepository;
import org.example.backend.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    private final IngredientUsageRepository ingredientUsageRepository;
    private final RecipeRepository recipeRepository;

    public RecipeService(IngredientUsageRepository ingredientUsageRepository, RecipeRepository recipeRepository) {
        this.ingredientUsageRepository = ingredientUsageRepository;
        this.recipeRepository = recipeRepository;
    }

    private Recipe saveRecipe(RecipeRequestDto recipeRequestDto) {
        //TODO Methode schrijven
    }
}
