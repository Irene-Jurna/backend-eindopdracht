package org.example.backend.services;

import jakarta.transaction.Transactional;
import org.example.backend.dtos.ImageRequestDto;
import org.example.backend.dtos.ImageResponseDto;
import org.example.backend.exceptions.ResourceNotFoundException;
import org.example.backend.mappers.ImageMapper;
import org.example.backend.models.Image;
import org.example.backend.models.Recipe;
import org.example.backend.repositories.ImageRepository;
import org.example.backend.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final RecipeRepository recipeRepository;

    public ImageService(ImageRepository imageRepository, RecipeRepository recipeRepository) {
        this.imageRepository = imageRepository;
        this.recipeRepository = recipeRepository;
    }

    public ImageResponseDto uploadImage(ImageRequestDto dto) {
        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException("Recept niet gevonden"));

        Image image = ImageMapper.toEntity(dto);
        image.setFileName(dto.getFile().getOriginalFilename());
        image.setRecipe(recipe);

        Image savedImage = imageRepository.save(image);
        return ImageMapper.toDto(savedImage);
    }

    @Transactional
    public ImageResponseDto getImageByRecipeId(Long recipeId) {
        Image image = imageRepository.findByRecipeId(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Afbeelding niet gevonden"));
        return ImageMapper.toDto(image);
    }

    @Transactional
    public Image getImageEntityByRecipeId(Long recipeId) {
        return imageRepository.findByRecipeId(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Afbeelding niet gevonden"));
    }

    @Transactional
    public void deleteImageByRecipeId(Long recipeId) {
        if (!imageRepository.existsByRecipeId(recipeId)) {
            throw new ResourceNotFoundException("Afbeelding voor recept met id " + recipeId + " niet gevonden");
        }

        imageRepository.deleteByRecipeId(recipeId);
    }
}
