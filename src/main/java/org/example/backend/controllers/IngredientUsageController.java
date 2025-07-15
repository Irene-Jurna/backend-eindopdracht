package org.example.backend.controllers;

import org.example.backend.dtos.IngredientResponseDto;
import org.example.backend.dtos.IngredientUsageRequestDto;
import org.example.backend.dtos.IngredientUsageResponseDto;
import org.example.backend.mappers.IngredientMapper;
import org.example.backend.mappers.IngredientUsageMapper;
import org.example.backend.models.IngredientUsage;
import org.example.backend.services.IngredientUsageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient-usage")
public class IngredientUsageController {

    private final IngredientUsageService ingredientUsageService;

    public IngredientUsageController(IngredientUsageService ingredientUsageService) {
        this.ingredientUsageService = ingredientUsageService;
    }

    @PostMapping
    public ResponseEntity<IngredientUsageResponseDto> createIngredientUsage(@RequestBody IngredientUsageRequestDto ingredientUsageRequestDto) {
        IngredientUsage savedUsage = ingredientUsageService.saveIngredientUsage(ingredientUsageRequestDto);
        IngredientResponseDto ingredientDto = IngredientMapper.toDto(savedUsage.getIngredient());
        IngredientUsageResponseDto ingredientUsageResponseDto = IngredientUsageMapper.toDto(savedUsage, ingredientDto);
        return ResponseEntity.ok(ingredientUsageResponseDto);
    }
}
