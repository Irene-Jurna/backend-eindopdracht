package org.example.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientRequestDto {
    private String type;
    @NotBlank(message = "Naam mag niet leeg zijn")
    @Size(min = 2, max = 50, message = "Naam moet minimaal 2 en maximaal 50 tekens zijn")
    public String name;

    // Alleen voor HarvestCrop
    private String about;
    private String harvestMethod;
    private String storageMethod;
}
