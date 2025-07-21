package org.example.backend.controllers;

import org.example.backend.dtos.IngredientUsageRequestDto;
import org.example.backend.dtos.IngredientUsageResponseDto;
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
        IngredientUsageResponseDto ingredientUsageResponseDto = IngredientUsageMapper.toDto(savedUsage);
        return ResponseEntity.ok(ingredientUsageResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientUsageResponseDto> getIngredientUsageById(@PathVariable Long id) {
        return ResponseEntity.ok(IngredientUsageMapper.toDto(ingredientUsageService.getIngredientUsageById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<IngredientUsageResponseDto> updateIngredientUsageById(@PathVariable Long id, @RequestBody IngredientUsageRequestDto ingredientUsageRequestDto) {
        IngredientUsage updatedUsage = ingredientUsageService.updateIngredientUsageById(id, ingredientUsageRequestDto);
        IngredientUsageResponseDto ingredientUsageResponseDto = IngredientUsageMapper.toDto(updatedUsage);
        return ResponseEntity.ok(ingredientUsageResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IngredientUsageResponseDto> deleteIngredientUsageById(@PathVariable Long id) {
        ingredientUsageService.deleteIngredientUsageById(id);
        return ResponseEntity.noContent().build();
    }
}
