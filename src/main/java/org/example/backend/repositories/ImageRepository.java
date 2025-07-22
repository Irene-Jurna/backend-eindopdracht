package org.example.backend.repositories;

import org.example.backend.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByRecipeId(Long recipeId);
    void deleteByRecipeId(Long recipeId);

    boolean existsByRecipeId(Long recipeId);
}
