package org.example.backend.services;

import org.example.backend.dtos.IngredientRequestDto;
import org.example.backend.dtos.IngredientResponseDto;
import org.example.backend.dtos.IngredientUpdateDto;
import org.example.backend.enums.IngredientType;
import org.example.backend.exceptions.DuplicateIngredientNameException;
import org.example.backend.exceptions.ResourceNotFoundException;
import org.example.backend.mappers.IngredientMapper;
import org.example.backend.models.HarvestCrop;
import org.example.backend.models.Ingredient;
import org.example.backend.models.StoreIngredient;
import org.example.backend.repositories.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient saveIngredient(IngredientRequestDto ingredientRequestDto) {
        if (ingredientRepository.existsByName(ingredientRequestDto.getName())) {
            throw new DuplicateIngredientNameException("Er bestaat al een ingredient met de naam: " + ingredientRequestDto.getName() + ". Kies een andere naam of gebruik het bestaande ingredient.");
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

    public Ingredient updateIngredient(Long id, IngredientUpdateDto dto) {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ingredient met id: " + id + " niet gevonden."));

        if (dto.getName() != null) {
            ingredient.setName(dto.getName());
        }

        if (ingredient instanceof HarvestCrop crop) {
            if (dto.getAbout() != null) {
                crop.setAbout(dto.getAbout());
            }
            if (dto.getHarvestMethod() != null) {
                crop.setHarvestMethod(dto.getHarvestMethod());
            }
            if (dto.getStorageMethod() != null) {
                crop.setStorageMethod(dto.getStorageMethod());
            }
        } else if (ingredient instanceof StoreIngredient store) {
            if (dto.getPointOfSale() != null) {
                store.setPointOfSale(dto.getPointOfSale());
            }
        }

        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(@PathVariable Long id) {
        if (ingredientRepository.existsById(id)) {
            ingredientRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Ingredient met id: " + id + " niet gevonden");
        }
    }
}
