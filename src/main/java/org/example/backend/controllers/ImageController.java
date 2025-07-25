package org.example.backend.controllers;

import org.example.backend.dtos.ImageRequestDto;
import org.example.backend.dtos.ImageResponseDto;
import org.example.backend.models.Image;
import org.example.backend.services.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.example.backend.utils.LocationUriHeaderUtil;

import java.net.URI;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/{recipeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponseDto> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Long recipeId) {
        ImageRequestDto requestDto = new ImageRequestDto();
        requestDto.setFile(file);
        requestDto.setRecipeId(recipeId);

        ImageResponseDto responseDto = imageService.uploadImage(requestDto);

        URI location = LocationUriHeaderUtil.createLocationUri(responseDto.getId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<ImageResponseDto> getImageByRecipeId(@PathVariable Long recipeId) {
        ImageResponseDto responseDto = imageService.getImageByRecipeId(recipeId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/download/{recipeId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long recipeId) {
        Image image = imageService.getImageEntityByRecipeId(recipeId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(new ByteArrayResource(image.getFile()));
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Object> deleteImage(@PathVariable Long recipeId) {
        imageService.deleteImageByRecipeId(recipeId);
        return ResponseEntity.noContent().build();
    }
}
