package org.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.models.Ingredient;

import java.math.BigDecimal;

@Getter
@Setter
public class IngredientUsageRequestDto {
    private Long ingredientId;
    private BigDecimal quantity;
    private String unit;
}
