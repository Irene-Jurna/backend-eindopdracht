package org.example.backend.services;

import org.example.backend.dtos.IngredientRequestDto;
import org.example.backend.dtos.IngredientResponseDto;
import org.example.backend.enums.IngredientType;
import org.example.backend.mappers.IngredientMapper;
import org.example.backend.models.HarvestCrop;
import org.example.backend.models.Ingredient;
import org.example.backend.models.StoreIngredient;
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
        if (ingredientRequestDto.getType() == null) {
            throw new IllegalArgumentException("Type is verplicht. Kies 'harvest' voor oogstingredienten en 'store' voor alle overige ingredienten.");
        }
        Ingredient ingredient = IngredientMapper.toEntity(ingredientRequestDto);
        return ingredientRepository.save(ingredient);
    }

    public List<IngredientResponseDto> getAllIngredients(IngredientType type) {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        if (type != null) {
            ingredients = ingredients.stream()
                    .filter(i -> (type == IngredientType.HARVEST && i instanceof HarvestCrop)
                            || (type == IngredientType.STORE && i instanceof StoreIngredient))
                    .toList();

        }
        return ingredients.stream().map(IngredientMapper::toDto).toList();
    }
}
