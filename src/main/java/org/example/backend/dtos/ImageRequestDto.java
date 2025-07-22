package org.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageRequestDto {
    private MultipartFile file;
    private Long recipeId;
}
