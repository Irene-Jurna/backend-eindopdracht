package org.example.backend.mappers;

import org.example.backend.dtos.IngredientRequestDto;
import org.example.backend.dtos.IngredientResponseDto;
import org.example.backend.enums.IngredientType;
import org.example.backend.models.HarvestCrop;
import org.example.backend.models.Ingredient;
import org.example.backend.models.StoreIngredient;

public class IngredientMapper {
    public static Ingredient toEntity(IngredientRequestDto ingredientRequestDto) {
        switch (ingredientRequestDto.getType()) {
            case HARVEST:
                HarvestCrop crop = new HarvestCrop();
                crop.setName(ingredientRequestDto.getName());
                crop.setAbout(ingredientRequestDto.getAbout());
                crop.setHarvestMethod(ingredientRequestDto.getHarvestMethod());
                crop.setStorageMethod(ingredientRequestDto.getStorageMethod());
                return crop;

            case STORE:
                StoreIngredient ingredient = new StoreIngredient();
                ingredient.setName(ingredientRequestDto.getName());
                ingredient.setPointOfSale(ingredientRequestDto.getPointOfSale());
                return ingredient;

            default:
                throw new IllegalArgumentException("Onbekend type: " + ingredientRequestDto.getType());
        }
    }

    public static IngredientResponseDto toDto(Ingredient ingredient) {
        IngredientResponseDto ingredientResponseDto = new IngredientResponseDto();
        ingredientResponseDto.setId(ingredient.getId());
        ingredientResponseDto.setName(ingredient.getName());

        if  (ingredient instanceof HarvestCrop crop) {
            ingredientResponseDto.setType(IngredientType.HARVEST);
            ingredientResponseDto.setAbout(crop.getAbout());
            ingredientResponseDto.setHarvestMethod(crop.getHarvestMethod());
            ingredientResponseDto.setStorageMethod(crop.getStorageMethod());
        } else if (ingredient instanceof StoreIngredient store) {
            ingredientResponseDto.setType(IngredientType.STORE);
            ingredientResponseDto.setPointOfSale(store.getPointOfSale());
        }
        return ingredientResponseDto;
    }
}
