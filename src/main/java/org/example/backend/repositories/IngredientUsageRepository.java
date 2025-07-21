package org.example.backend.repositories;

import org.example.backend.models.IngredientUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientUsageRepository extends JpaRepository<IngredientUsage, Long> {
}
