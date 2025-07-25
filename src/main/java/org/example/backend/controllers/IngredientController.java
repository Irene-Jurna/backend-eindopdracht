package org.example.backend.controllers;

import jakarta.validation.Valid;
import org.example.backend.dtos.IngredientRequestDto;
import org.example.backend.dtos.IngredientResponseDto;
import org.example.backend.dtos.IngredientUpdateDto;
import org.example.backend.enums.IngredientType;
import org.example.backend.mappers.IngredientMapper;
import org.example.backend.models.Ingredient;
import org.example.backend.security.MyUserDetails;
import org.example.backend.services.IngredientService;
import org.example.backend.utils.LocationUriHeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping()
    public ResponseEntity<IngredientResponseDto> createIngredient(@RequestBody @Valid IngredientRequestDto ingredientRequestDto, @AuthenticationPrincipal MyUserDetails userDetails) {
        System.out.println("Gebruiker roles: " + userDetails.getAuthorities());

        Ingredient savedIngredient = ingredientService.saveIngredient(ingredientRequestDto);
        IngredientResponseDto ingredientResponseDto = IngredientMapper.toDto(savedIngredient);

        URI location = LocationUriHeaderUtil.createLocationUri(ingredientResponseDto.getId());
        return ResponseEntity.created(location).body(ingredientResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<IngredientResponseDto>> getAllIngredients(@RequestParam(required=false) IngredientType type) {
        return ResponseEntity.ok(ingredientService.getAllIngredients(type));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<IngredientResponseDto> updateIngredient(@PathVariable Long id, @RequestBody @Valid IngredientUpdateDto ingredientUpdateDto, @AuthenticationPrincipal MyUserDetails userDetails) {
        Ingredient ingredientToUpdate = ingredientService.updateIngredient(id, ingredientUpdateDto);
        IngredientResponseDto ingredientResponseDto = IngredientMapper.toDto(ingredientToUpdate);
        return ResponseEntity.ok(ingredientResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IngredientResponseDto> deleteIngredient(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails userDetails) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}
