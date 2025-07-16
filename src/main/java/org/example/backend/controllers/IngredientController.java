package org.example.backend.controllers;

import jakarta.validation.Valid;
import org.example.backend.dtos.IngredientRequestDto;
import org.example.backend.dtos.IngredientResponseDto;
import org.example.backend.dtos.IngredientUpdateDto;
import org.example.backend.enums.IngredientType;
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
    public ResponseEntity<List<IngredientResponseDto>> getAllIngredients(@RequestParam(required=false) IngredientType type) {
        return ResponseEntity.ok(ingredientService.getAllIngredients(type));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<IngredientResponseDto> updateIngredient(@PathVariable Long id, @RequestBody @Valid IngredientUpdateDto ingredientUpdateDto) {
        Ingredient ingredientToUpdate = ingredientService.updateIngredient(id, ingredientUpdateDto);
        IngredientResponseDto ingredientResponseDto = IngredientMapper.toDto(ingredientToUpdate);
        return ResponseEntity.ok(ingredientResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IngredientResponseDto> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}
