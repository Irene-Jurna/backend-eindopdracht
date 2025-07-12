package org.example.backend.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.IngredientType;

@Getter
@Setter
public class IngredientRequestDto {
    private IngredientType type;
    @Size(min = 2, max = 50, message = "Naam moet minimaal 2 en maximaal 50 tekens zijn")
    private String name;

    // Alleen voor HarvestCrop
    private String about;
    private String harvestMethod;
    private String storageMethod;
}
