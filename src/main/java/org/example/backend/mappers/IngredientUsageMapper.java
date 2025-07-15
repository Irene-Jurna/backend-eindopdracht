package org.example.backend.mappers;

import org.example.backend.dtos.IngredientResponseDto;
import org.example.backend.dtos.IngredientUsageRequestDto;
import org.example.backend.dtos.IngredientUsageResponseDto;
import org.example.backend.models.Ingredient;
import org.example.backend.models.IngredientUsage;

public class IngredientUsageMapper {
    public static IngredientUsage toEntity(IngredientUsageRequestDto dto, Ingredient ingredient) {
        IngredientUsage usage = new IngredientUsage();
        usage.setIngredient(ingredient);
        usage.setQuantity(dto.getQuantity());
        usage.setUnit(dto.getUnit());
        return usage;
    }

    public static IngredientUsageResponseDto toDto(IngredientUsage usage, IngredientResponseDto ingredient) {
        IngredientUsageResponseDto responseDto = new IngredientUsageResponseDto();
        responseDto.setId(usage.getId());
        responseDto.setIngredientName(usage.getIngredient().getName());
        responseDto.setQuantity(usage.getQuantity());
        responseDto.setUnit(usage.getUnit());
        return responseDto;
    }
}
