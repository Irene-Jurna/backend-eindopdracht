package org.example.backend.controllers;

import jakarta.validation.Valid;
import org.example.backend.dtos.IngredientRequestDto;
import org.example.backend.dtos.IngredientResponseDto;
import org.example.backend.mappers.IngredientMapper;
import org.example.backend.models.Ingredient;
import org.example.backend.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping()
    public ResponseEntity<IngredientResponseDto> createIngredient(@RequestBody @Valid IngredientRequestDto ingredientRequestDto) {
        Ingredient savedIngredient = ingredientService.saveIngredient(ingredientRequestDto);
        IngredientResponseDto ingredientResponseDto = IngredientMapper.toDto(savedIngredient);
        return ResponseEntity.ok(ingredientResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<IngredientResponseDto>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }
}
