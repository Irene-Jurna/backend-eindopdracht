package org.example.backend.services;

import org.example.backend.dtos.ImageRequestDto;
import org.example.backend.dtos.ImageResponseDto;
import org.example.backend.mappers.ImageMapper;
import org.example.backend.models.Image;
import org.example.backend.models.Recipe;
import org.example.backend.repositories.ImageRepository;
import org.example.backend.repositories.RecipeRepository;
import org.example.backend.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Deze tests zijn gemaakt door ChatGPT (paar iteraties gehad met foutmeldingen en ik wilde MockitoExtension gebruiken zoals in de les is behandeld)
@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private ImageService imageService;

    private MockedStatic<ImageMapper> imageMapperMock;

    @BeforeEach
    void setUp() {
        imageMapperMock = mockStatic(ImageMapper.class);
    }

    @AfterEach
    void tearDown() {
        imageMapperMock.close();
    }

    @Test
    @DisplayName("Should save image and return uploaded image")
    void uploadImage() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", new byte[]{1, 2, 3});
        ImageRequestDto dto = new ImageRequestDto();
        dto.setFile(file);
        dto.setRecipeId(1L);

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Image mappedImage = new Image();
        Image savedImage = new Image();
        ImageResponseDto responseDto = new ImageResponseDto();

        when(recipeRepository.findById(dto.getRecipeId())).thenReturn(Optional.of(recipe));

        imageMapperMock.when(() -> ImageMapper.toEntity(dto)).thenReturn(mappedImage);
        imageMapperMock.when(() -> ImageMapper.toDto(savedImage)).thenReturn(responseDto);

        when(imageRepository.save(mappedImage)).thenReturn(savedImage);

        // Act
        ImageResponseDto result = imageService.uploadImage(dto);

        // Assert
        assertEquals(responseDto, result);
        verify(recipeRepository).findById(dto.getRecipeId());
        verify(imageRepository).save(mappedImage);
        assertEquals("test.png", mappedImage.getFileName());
        assertEquals(recipe, mappedImage.getRecipe());
    }

    @Test
    @DisplayName("Should throw exception when image upload has incorrect recipe id")
    void uploadImageThrowsException() {
        ImageRequestDto dto = new ImageRequestDto();
        dto.setRecipeId(999L);
        dto.setFile(new MockMultipartFile("file", "test.png", "image/png", new byte[]{1}));

        when(recipeRepository.findById(dto.getRecipeId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> imageService.uploadImage(dto));

        verify(recipeRepository).findById(dto.getRecipeId());
        verifyNoMoreInteractions(imageRepository);
    }

    @Test
    @DisplayName("Should get image of recipe with id 1")
    void getImageByRecipeId() {
        // Arrange
        Long recipeId = 1L;
        Image image = new Image();
        image.setId(1L);

        ImageResponseDto dto = new ImageResponseDto();
        dto.setFileName("test.jpg");

        when(imageRepository.findByRecipeId(recipeId)).thenReturn(Optional.of(image));
        imageMapperMock.when(() -> ImageMapper.toDto(image)).thenReturn(dto);

        // Act
        ImageResponseDto result = imageService.getImageByRecipeId(recipeId);

        // Assert
        assertEquals("test.jpg", result.getFileName());
    }

    @Test
    @DisplayName("Should throw exception for requested image when recipe id is incorrect")
    void getImageByRecipeIdThrowsException() {
        when(imageRepository.findByRecipeId(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                imageService.getImageByRecipeId(99L));
    }

    @Test
    @DisplayName("Should get download information of requested image by recipe id 1")
    void getImageEntityByRecipeId() {
        Long recipeId = 1L;
        Image image = new Image();
        when(imageRepository.findByRecipeId(recipeId)).thenReturn(Optional.of(image));

        Image result = imageService.getImageEntityByRecipeId(recipeId);
        assertEquals(image, result);
    }

    @Test
    @DisplayName("Should throw exception when download image is requested with incorrect recipe id")
    void getImageEntityByRecipeIdThrowsException() {
        when(imageRepository.findByRecipeId(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                imageService.getImageEntityByRecipeId(99L));
    }

    @Test
    @DisplayName("Should delete image if recipe id exists")
    void deleteImageByRecipeId() {
        Long recipeId = 1L;
        when(imageRepository.existsByRecipeId(recipeId)).thenReturn(true);

        imageService.deleteImageByRecipeId(recipeId);

        verify(imageRepository).deleteByRecipeId(recipeId);
    }

    @Test
    @DisplayName("Should throw exception when user wants to delete an image with incorrect recipe id")
    void deleteImageByRecipeId_notFound() {
        when(imageRepository.existsByRecipeId(42L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () ->
                imageService.deleteImageByRecipeId(42L));
    }

}
