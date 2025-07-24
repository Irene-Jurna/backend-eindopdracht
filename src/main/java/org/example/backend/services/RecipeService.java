package org.example.backend.services;

import org.example.backend.dtos.IngredientUsageRequestDto;
import org.example.backend.dtos.RecipeRequestDto;
import org.example.backend.dtos.RecipeResponseDto;
import org.example.backend.enums.CuisineType;
import org.example.backend.enums.DishType;
import org.example.backend.enums.HarvestMonth;
import org.example.backend.exceptions.ResourceNotFoundException;
import org.example.backend.exceptions.AccessDeniedException;
import org.example.backend.mappers.IngredientUsageMapper;
import org.example.backend.mappers.RecipeMapper;
import org.example.backend.models.Ingredient;
import org.example.backend.models.IngredientUsage;
import org.example.backend.models.Recipe;
import org.example.backend.models.User;
import org.example.backend.repositories.IngredientRepository;
import org.example.backend.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    public RecipeService(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    public Recipe saveRecipe(RecipeRequestDto recipeRequestDto, User user) {
        List<IngredientUsage> ingredientUsages = new ArrayList<>();

        // Haal het juiste ingredient op met de id
        for (IngredientUsageRequestDto usageRequestDto : recipeRequestDto.getIngredients()) {
            Ingredient ingredient = ingredientRepository.findById(usageRequestDto.getIngredientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient-id niet gevonden"));

            // Maak een ingredientUsage aan met een mapper
            IngredientUsage usage = IngredientUsageMapper.toEntity(usageRequestDto, ingredient);
            ingredientUsages.add(usage);
        }

        // Maak een recept aan via de mapper
        Recipe recipe = RecipeMapper.toEntity(recipeRequestDto, ingredientUsages, user);

        // Leg de relatie tussen IngredientUsages en Recept
        for (IngredientUsage usage : ingredientUsages) {
            usage.setRecipe(recipe);
        }

        return recipeRepository.save(recipe);
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recept met id " + id + " niet gevonden."));
    }

    public List<RecipeResponseDto> getAllRecipes(
            CuisineType cuisineType,
            DishType dishType,
            HarvestMonth harvestMonth,
            List<Long> ingredientIds,
            String season)
    {

        List<Recipe> recipes = recipeRepository.findAll();

        if (cuisineType != null) {
            recipes = recipes.stream()
                    .filter(r -> r.getCuisineType().equals(cuisineType))
                    .toList();
        }

        if (dishType != null) {
            recipes = recipes.stream()
                    .filter(r -> r.getDishType().equals(dishType))
                    .toList();
        }

        if (harvestMonth != null) {
            recipes = recipes.stream()
                    .filter(r -> r.getHarvestMonth().contains(harvestMonth))
                    .toList();
        }

        if (ingredientIds != null && !ingredientIds.isEmpty()) {
            for (Long ingredientId : ingredientIds) {
                ingredientRepository.findById(ingredientId).orElseThrow(() -> new ResourceNotFoundException("Ingredient-id " + ingredientId + " niet gevonden"));
            }

            recipes = recipes.stream()
                    .filter(r -> ingredientIds.stream()
                            .allMatch(id -> r.getIngredients().stream()
                                    .anyMatch(u -> u.getIngredient().getId().equals(id))))
                    .toList();
        }

        if (season != null) {
            List<HarvestMonth> seasonMonths = switch (season.toLowerCase()) {
                case "lente" -> List.of(HarvestMonth.MAR, HarvestMonth.APR, HarvestMonth.MAY);
                case "zomer" -> List.of(HarvestMonth.JUN, HarvestMonth.JUL, HarvestMonth.AUG);
                case "herfst" -> List.of(HarvestMonth.SEP, HarvestMonth.OCT, HarvestMonth.NOV);
                case "winter" -> List.of(HarvestMonth.DEC, HarvestMonth.JAN, HarvestMonth.FEB);
                default -> List.of();
            };

            recipes = recipes.stream()
                    .filter(r -> r.getHarvestMonth().stream().anyMatch(seasonMonths::contains)).toList();
        }

        return recipes.stream().map(RecipeMapper::toDto).toList();
    }

    public Recipe updateRecipe(Long id, RecipeRequestDto dto, String userEmail) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recept met id " + id + " niet gevonden."));

        if (!recipe.getCreatedBy().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("Je hebt dit recept niet aangemaakt en mag het dus ook niet aanpassen.");
        }

        if (dto.getTitle() != null) {
            recipe.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            recipe.setDescription(dto.getDescription());
        }

        if (dto.getHarvestMonth() != null) {
            recipe.setHarvestMonth(dto.getHarvestMonth());
        }

        if (dto.getCuisineType() != null) {
            recipe.setCuisineType(dto.getCuisineType());
        }

        if (dto.getDishType() != null) {
            recipe.setDishType(dto.getDishType());
        }

        if (dto.getIngredients() != null) {
            List<IngredientUsage> updatedUsages = dto.getIngredients().stream()
                    .map(usageDto -> {
                        Ingredient ingredient = ingredientRepository.findById(usageDto.getIngredientId())
                                .orElseThrow(() -> new ResourceNotFoundException("Ingredient-id niet gevonden"));

                        return IngredientUsageMapper.toEntity(usageDto, ingredient);
                    }).toList();
        }

        if (dto.getCookingSteps()  != null) {
            recipe.setCookingSteps(dto.getCookingSteps());
        }

        return recipeRepository.save(recipe);
    }

    public void deleteRecipeById(Long id, String userEmail) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recept id niet gevonden."));

        if (!recipe.getCreatedBy().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("Je hebt dit recept niet aangemaakt en mag het daarom niet verwijderen");
        }

        recipeRepository.delete(recipe);
    }
}
