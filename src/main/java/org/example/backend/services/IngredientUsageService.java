package org.example.backend.services;

import org.example.backend.dtos.IngredientUsageRequestDto;
import org.example.backend.exceptions.ResourceNotFoundException;
import org.example.backend.mappers.IngredientUsageMapper;
import org.example.backend.models.Ingredient;
import org.example.backend.models.IngredientUsage;
import org.example.backend.repositories.IngredientRepository;
import org.example.backend.repositories.IngredientUsageRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientUsageService {

    private final IngredientUsageRepository ingredientUsageRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientUsageService(IngredientUsageRepository ingredientUsageRepository, IngredientRepository ingredientRepository) {
        this.ingredientUsageRepository = ingredientUsageRepository;
        this.ingredientRepository = ingredientRepository;
    }

    private IngredientUsage findIngredientUsageOrThrow(Long id, String action) {
        return ingredientUsageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot " + action + " ingredient usage because id " + id + " does not exist"));
    }

    public IngredientUsage saveIngredientUsage(IngredientUsageRequestDto dto) {
        Ingredient ingredient = ingredientRepository.findById(dto.getIngredientId()).orElseThrow(() -> new ResourceNotFoundException("Ingredient id niet gevonden"));
        IngredientUsage ingredientUsage = IngredientUsageMapper.toEntity(dto, ingredient);
        return ingredientUsageRepository.save(ingredientUsage);
    }

    public IngredientUsage getIngredientUsageById(Long id) {
        return findIngredientUsageOrThrow(id, "find");
    }

    public IngredientUsage updateIngredientUsageById(Long id, IngredientUsageRequestDto dto) {
        IngredientUsage ingredientUsage = findIngredientUsageOrThrow(id, "update");

        if (dto.getIngredientId() != null) {
            Ingredient ingredient = ingredientRepository.findById(dto.getIngredientId()).orElseThrow(() -> new ResourceNotFoundException("Ingredient id niet gevonden"));
            ingredientUsage.setIngredient(ingredient);
        }

        if (dto.getQuantity() != null) {
            ingredientUsage.setQuantity(dto.getQuantity());
        }

        if (dto.getUnit() != null) {
            ingredientUsage.setUnit(dto.getUnit());
        }

        return ingredientUsageRepository.save(ingredientUsage);
    }

    public void deleteIngredientUsageById(Long id) {
        IngredientUsage usage = findIngredientUsageOrThrow(id, "delete");
        this.ingredientUsageRepository.delete(usage);
    }
}
