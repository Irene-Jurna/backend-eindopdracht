package org.example.backend.mappers;

import org.example.backend.dtos.IngredientRequestDto;
import org.example.backend.dtos.IngredientResponseDto;
import org.example.backend.models.HarvestCrop;
import org.example.backend.models.Ingredient;
import org.example.backend.models.StoreIngredient;

public class IngredientMapper {
    public static Ingredient toEntity(IngredientRequestDto ingredientRequestDto) {
        if (ingredientRequestDto.getType() == null) {
            throw new IllegalArgumentException("Type klopt niet, kies voor 'harvest' als het een oogstgewas is, of 'store' voor andere producten");
        }

        switch (ingredientRequestDto.getType().toLowerCase()) {
            case "harvest":
                HarvestCrop crop = new HarvestCrop();
                crop.setName(ingredientRequestDto.getName());
                crop.setAbout(ingredientRequestDto.getAbout());
                crop.setHarvestMethod(ingredientRequestDto.getHarvestMethod());
                crop.setStorageMethod(ingredientRequestDto.getStorageMethod());
                return crop;

            case "store":
                StoreIngredient ingredient = new StoreIngredient();
                ingredient.setName(ingredientRequestDto.getName());
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
            ingredientResponseDto.setType("harvest");
            ingredientResponseDto.setAbout(crop.getAbout());
            ingredientResponseDto.setHarvestMethod(crop.getHarvestMethod());
            ingredientResponseDto.setStorageMethod(crop.getStorageMethod());
        } else if (ingredient instanceof StoreIngredient) {
            ingredientResponseDto.setType("store");
        }
        return ingredientResponseDto;
    }
}
