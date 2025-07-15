package org.example.backend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientUsageResponseDto {
    private Long id;
    private String ingredientName;
    private BigDecimal quantity;
    private String unit;
}
