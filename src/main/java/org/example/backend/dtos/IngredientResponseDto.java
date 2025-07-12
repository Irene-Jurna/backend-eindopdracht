package org.example.backend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IngredientResponseDto {
    private Long id;
    private String type;
    private String name;

    // Alleen voor HarvestCrop
    private String about;
    private String harvestMethod;
    private String storageMethod;
}
