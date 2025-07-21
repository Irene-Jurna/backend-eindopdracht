package org.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.enums.IngredientType;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientResponseDto {
    private Long id;
    private IngredientType type;
    private String name;

    // Alleen voor StoreIngredient
    private String pointOfSale;

    // Alleen voor HarvestCrop
    private String about;
    private String harvestMethod;
    private String storageMethod;
}
