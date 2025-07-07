package org.example.backend.services;

import org.example.backend.models.HarvestCrop;
import org.example.backend.models.Ingredient;
import org.example.backend.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public HarvestCrop saveHarvestCrop(HarvestCrop crop) {
        return ingredientRepository.save(crop);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }
}
