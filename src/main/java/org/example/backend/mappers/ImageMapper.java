package org.example.backend.mappers;

import org.example.backend.dtos.ImageRequestDto;
import org.example.backend.dtos.ImageResponseDto;
import org.example.backend.models.Image;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ImageMapper {
    public static Image toEntity(ImageRequestDto dto) {
        Image image = new Image();

        try {
            image.setFile(dto.getFile().getBytes());
            image.setFileName(dto.getFile().getOriginalFilename());
        } catch (Exception e) {
            throw new RuntimeException("Fout bij het lezen van dit bestand");
        }

        return image;
    }

    public static ImageResponseDto toDto(Image image) {
        ImageResponseDto dto = new ImageResponseDto();
        dto.setId(image.getId());
        dto.setFileName(image.getFileName());

        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/download/")
                .path(image.getId().toString())
                .toUriString();

        dto.setDownloadUri(downloadUri);

        if (image.getRecipe() != null) {
            dto.setRecipeName(image.getRecipe().getTitle());
        }
        return dto;
    }
}
