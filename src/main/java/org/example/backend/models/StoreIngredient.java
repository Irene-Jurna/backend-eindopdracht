package org.example.backend.models;

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
    public StoreIngredient(String name) {
        super(name);
    }
}
