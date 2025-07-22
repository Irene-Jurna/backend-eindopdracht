package org.example.backend.controllers;

import org.example.backend.dtos.RecipeRequestDto;
import org.example.backend.dtos.RecipeResponseDto;
import org.example.backend.mappers.RecipeMapper;
import org.example.backend.models.Recipe;
import org.example.backend.security.MyUserDetails;
import org.example.backend.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDto> createRecipe(@RequestBody RecipeRequestDto recipeRequestDto, @AuthenticationPrincipal MyUserDetails userDetails) {
        Recipe savedRecipe = recipeService.saveRecipe(recipeRequestDto, userDetails.getUser());
        RecipeResponseDto recipeResponseDto = RecipeMapper.toDto(savedRecipe);
        return ResponseEntity.ok(recipeResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(RecipeMapper.toDto(recipeService.getRecipeById(id)));
    }

    @GetMapping
    public ResponseEntity<List<RecipeResponseDto>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> updateRecipe(@RequestBody RecipeRequestDto recipeRequestDto, @PathVariable Long id, @AuthenticationPrincipal MyUserDetails userDetails) {
        Recipe updatedRecipe = recipeService.updateRecipe(id, recipeRequestDto, userDetails.getUser().getEmail());
        RecipeResponseDto recipeResponseDto = RecipeMapper.toDto(updatedRecipe);
        return ResponseEntity.ok(recipeResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> deleteRecipeById(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails userDetails) {
        recipeService.deleteRecipeById(id, userDetails.getUser().getEmail());
        return ResponseEntity.noContent().build();
    }
}
