package org.example.backend.repositories;

import jakarta.validation.constraints.Size;
import org.example.backend.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    boolean existsByName(@Size(min = 2, max = 50, message = "Naam moet minimaal 2 en maximaal 50 tekens zijn.") String name);
}
