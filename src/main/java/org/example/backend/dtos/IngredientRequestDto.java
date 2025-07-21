package org.example.backend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.enums.IngredientType;

@Getter
@Setter
public class IngredientRequestDto {

    @NotNull(message = "Vul het type in. Check of je de waarde van type goed hebt gespeld (ben je haakjes, quotes of komma's vergeten?)")
    private IngredientType type;

    @Size(min = 2, max = 50, message = "Naam moet minimaal 2 en maximaal 50 tekens zijn.")
    private String name;

    // Alleen voor StoreIngredient
    @Size(max = 100, message = "te lang. De tekstinvoer mag maximaal 100 tekens bevatten.")
    private String pointOfSale;

    // Alleen voor HarvestCrop
    @Size(max = 1000, message = "te lang. De tekstinvoer mag maximaal 1000 tekens bevatten.")
    private String about;

    @Size(max = 500, message = "te lang. De tekstinvoer mag maximaal 500 tekens bevatten.")
    private String harvestMethod;

    @Size(max = 500, message = "te lang. De tekstinvoer mag maximaal 500 tekens bevatten.")
    private String storageMethod;
}
