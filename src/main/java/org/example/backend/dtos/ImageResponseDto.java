package org.example.backend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ImageResponseDto {
    private Long id;
    private String fileName;
    private String downloadUri;
    private String recipeName;
}
