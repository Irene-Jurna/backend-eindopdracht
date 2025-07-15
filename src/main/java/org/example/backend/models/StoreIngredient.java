package org.example.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "store_ingredients")
@Getter
@Setter
@NoArgsConstructor
public class StoreIngredient extends Ingredient {

    @Column(length = 100)
    private String pointOfSale;

    public StoreIngredient(String name) {
        super(name);
    }

    public StoreIngredient(String name, String pointOfSale) {
        super(name);
        this.pointOfSale = pointOfSale;
    }
}
