package org.example.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "harvest_crops")
@Getter
@Setter
@NoArgsConstructor
public class HarvestCrop extends Ingredient {

    @Lob
    private String about;

    @Lob
    private String harvestMethod;

    @Lob
    private String storageMethod;

    public HarvestCrop(String name, String about, String harvestMethod, String storageMethod) {
        super(name);
        this.about = about;
        this.harvestMethod = harvestMethod;
        this.storageMethod = storageMethod;
    }
}
