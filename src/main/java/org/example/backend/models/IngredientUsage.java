package org.example.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "ingredient_usages")
@Getter
@Setter
@NoArgsConstructor
public class IngredientUsage extends BaseModel {

    @ManyToOne(optional = false)
    private Ingredient ingredient;

    private BigDecimal quantity;
    private String unit;

    @ManyToOne
    private Recipe recipe;
}
