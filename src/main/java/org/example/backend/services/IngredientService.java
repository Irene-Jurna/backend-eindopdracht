package org.example.backend.services;

import org.example.backend.dtos.IngredientRequestDto;
import org.example.backend.dtos.IngredientResponseDto;
import org.example.backend.mappers.IngredientMapper;
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

    public Ingredient saveIngredient(IngredientRequestDto ingredientRequestDto) {
        Ingredient ingredient = IngredientMapper.toEntity(ingredientRequestDto);
        return ingredientRepository.save(ingredient);
    }

    public List<IngredientResponseDto> getAllIngredients() {
        return ingredientRepository.findAll().stream().map(IngredientMapper::toDto).toList();
    }
}
