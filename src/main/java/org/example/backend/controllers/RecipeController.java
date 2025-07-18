package org.example.backend.controllers;

import org.example.backend.dtos.RecipeRequestDto;
import org.example.backend.dtos.RecipeResponseDto;
import org.example.backend.mappers.RecipeMapper;
import org.example.backend.models.Recipe;
import org.example.backend.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDto> createRecipe(@RequestBody RecipeRequestDto recipeRequestDto) {
        Recipe savedRecipe = recipeService.saveRecipe(recipeRequestDto);
        RecipeResponseDto recipeResponseDto = RecipeMapper.toDto(savedRecipe);
        return ResponseEntity.ok(recipeResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(RecipeMapper.toDto(recipeService.getRecipeById(id)));
    }
}
