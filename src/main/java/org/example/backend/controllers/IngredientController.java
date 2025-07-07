package org.example.backend.controllers;

import org.example.backend.models.HarvestCrop;
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

    @PostMapping("/harvest-crop")
    public ResponseEntity<HarvestCrop> createHarvestCrop(@RequestBody HarvestCrop crop) {
        HarvestCrop savedCrop = ingredientService.saveHarvestCrop(crop);
        return ResponseEntity.ok(savedCrop);
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }
}
