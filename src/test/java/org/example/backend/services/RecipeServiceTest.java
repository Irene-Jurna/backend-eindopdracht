package org.example.backend.services;

import org.example.backend.dtos.IngredientUsageRequestDto;
import org.example.backend.dtos.RecipeRequestDto;
import org.example.backend.dtos.RecipeResponseDto;
import org.example.backend.enums.CuisineType;
import org.example.backend.enums.DishType;
import org.example.backend.enums.HarvestMonth;
import org.example.backend.exceptions.AccessDeniedException;
import org.example.backend.exceptions.ResourceNotFoundException;
import org.example.backend.models.*;
import org.example.backend.repositories.IngredientRepository;
import org.example.backend.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.example.backend.enums.CuisineType.VEGETARIAN;
import static org.example.backend.enums.DishType.SWEET;
import static org.example.backend.enums.HarvestMonth.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    Recipe recipe;
    String title;
    String description;
    List<String> cookingSteps;
    List<HarvestMonth> harvestMonth;
    CuisineType cuisineType;
    DishType dishType;
    List<IngredientUsage> ingredients;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private User createdByMock;

    @Mock
    private Image imageMock;

    @BeforeEach
    void setUp() {
        title = "Venkel-ijs zonder ijsmachine";
        description = "In 5 minuten maak je super lekker ijs met je vers geoogste venkel van de pluktuin";
        cookingSteps = List.of(
                "Stap 1: Snijd de venkelknol in stukken",
                "Stap 2: Doe de venkel met de rest van de ingredienten in de keukenmachine",
                "Stap 3: Pureer kort op de hoogste stand en zet het ijs 24 uur in een cakeblik in de vriezer"
        );
        harvestMonth = List.of(MAR, JUN, JUL);
        cuisineType = VEGETARIAN;
        dishType = SWEET;
        ingredients = new java.util.ArrayList<>();

        IngredientUsage ingredientUsage1 = new IngredientUsage();
        ingredientUsage1.setIngredient(mock(Ingredient.class));
        ingredients.add(ingredientUsage1);

        IngredientUsage ingredientUsage2 = new IngredientUsage();
        ingredientUsage2.setIngredient(mock(Ingredient.class));
        ingredients.add(ingredientUsage2);

        recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setCookingSteps(cookingSteps);
        recipe.setHarvestMonth(harvestMonth);
        recipe.setCuisineType(cuisineType);
        recipe.setDishType(dishType);
        recipe.setIngredients(ingredients);
        recipe.setCreatedBy(createdByMock);
        recipe.setImage(imageMock);
    }

    @Test
    @DisplayName("Should save recipe and ingredientUsages")
    void saveRecipe() {
        // Arrange
        IngredientUsageRequestDto usageDto = new IngredientUsageRequestDto();
        usageDto.setIngredientId(1L);
        usageDto.setQuantity(BigDecimal.valueOf(200));
        usageDto.setUnit("gram");

        RecipeRequestDto recipeRequestDto = new RecipeRequestDto();
        recipeRequestDto.setTitle(title);
        recipeRequestDto.setDescription(description);
        recipeRequestDto.setCookingSteps(cookingSteps);
        recipeRequestDto.setHarvestMonth(harvestMonth);
        recipeRequestDto.setCuisineType(cuisineType);
        recipeRequestDto.setDishType(dishType);
        recipeRequestDto.setIngredients(List.of(usageDto));

        Ingredient ingredient = new HarvestCrop();
        ingredient.setId(1L);

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(recipeRepository.save(any(Recipe.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Recipe savedRecipe = recipeService.saveRecipe(recipeRequestDto, createdByMock);
        IngredientUsage savedUsage = savedRecipe.getIngredients().get(0);

        // Assert
        assertEquals(title, savedRecipe.getTitle());
        assertEquals(description, savedRecipe.getDescription());
        assertEquals(harvestMonth, savedRecipe.getHarvestMonth());
        assertEquals(cookingSteps, savedRecipe.getCookingSteps());
        assertEquals(1, savedRecipe.getIngredients().size());

        assertEquals(ingredient, savedUsage.getIngredient());
        assertEquals(savedRecipe, savedUsage.getRecipe());

        verify(ingredientRepository, times(1)).findById(1L);
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    @DisplayName("Should get recipe by id")
    void getRecipeById() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Recipe foundRecipe = recipeService.getRecipeById(1L);

        assertEquals(recipe, foundRecipe);
        verify(recipeRepository).findById(1L);
    }

    @Test
    @DisplayName("Should return recipes with search filters")
    void getAllRecipesWithFilters() {
        // Arrange
        Recipe recipe2 = new Recipe();
        recipe2.setCuisineType(cuisineType.VEGETARIAN);
        recipe2.setDishType(dishType.SWEET);
        recipe2.setHarvestMonth(List.of(JUL));
        recipe2.setIngredients(ingredients);

        when(recipeRepository.findAll()).thenReturn(List.of(recipe, recipe2));

        // Act
        List<RecipeResponseDto> result = recipeService.getAllRecipes(CuisineType.VEGETARIAN, dishType.SWEET, JUL, null, "Zomer");

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should return recipes that contain all given ingredient IDs")
    void getAllRecipesWithMatchingIngredients() {
        // Arrange
        Ingredient ingredient1 = new HarvestCrop();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new HarvestCrop();
        ingredient2.setId(2L);

        IngredientUsage usage1 = new IngredientUsage();
        usage1.setIngredient(ingredient1);
        IngredientUsage usage2 = new IngredientUsage();
        usage2.setIngredient(ingredient2);

        Recipe recipeWithIngredients = new Recipe();
        recipeWithIngredients.setIngredients(List.of(usage1, usage2));

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient1));
        when(ingredientRepository.findById(2L)).thenReturn(Optional.of(ingredient2));
        when(recipeRepository.findAll()).thenReturn(List.of(recipeWithIngredients));

        List<Long> ingredientIds = List.of(1L, 2L);

        // Act
        List<RecipeResponseDto> result = recipeService.getAllRecipes(null, null, null, ingredientIds, null);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should throw exception when ingredient ID not found")
    void getAllRecipesThrowsWhenIngredientNotFound() {
        // Arrange
        when(ingredientRepository.findById(99L)).thenReturn(Optional.empty());

        List<Long> ingredientIds = List.of(99L);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            recipeService.getAllRecipes(null, null, null, ingredientIds, null);
        });
    }



    @ParameterizedTest
    @MethodSource("seasonAndExpectedMonthProvider")
    @DisplayName("Should filter recipes correctly based on season filter")
    void getAllRecipesBySeason(String season, HarvestMonth expectedMonth) {
        // Arrange
        Recipe recipeThisSeason = new Recipe();
        recipeThisSeason.setIngredients(ingredients);
        recipeThisSeason.setHarvestMonth(List.of(expectedMonth));

        when(recipeRepository.findAll()).thenReturn(List.of(recipeThisSeason));

        // Act
        List<RecipeResponseDto> result = recipeService.getAllRecipes(null, null, null, null, season);

        // Assert
        assertEquals(1, result.size());
    }

    private static Stream<Arguments> seasonAndExpectedMonthProvider() {
        return Stream.of(
                Arguments.of("lente", HarvestMonth.APR),
                Arguments.of("Zomer", HarvestMonth.JUL),
                Arguments.of("HeRfsT", HarvestMonth.OCT),
                Arguments.of("WINTER", HarvestMonth.DEC)
        );
    }

    @Test
    @DisplayName("Should update all attributes of a recipe")
    void updateRecipeUpdatesAllFields() {
        // Arrange
        Ingredient ingredient = new HarvestCrop();
        ingredient.setId(1L);

        IngredientUsageRequestDto usageDto = new IngredientUsageRequestDto();
        usageDto.setIngredientId(1L);
        usageDto.setQuantity(BigDecimal.valueOf(200));
        usageDto.setUnit("gram");

        RecipeRequestDto dto = new RecipeRequestDto();
        dto.setTitle("Nieuwe titel");
        dto.setDescription("Nieuwe beschrijving");
        dto.setHarvestMonth(List.of(HarvestMonth.AUG));
        dto.setCuisineType(CuisineType.VEGETARIAN);
        dto.setDishType(DishType.SWEET);
        dto.setIngredients(List.of(usageDto));
        dto.setCookingSteps(List.of("Stap 1", "Stap 2"));

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenAnswer(i -> i.getArgument(0));
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(createdByMock.getEmail()).thenReturn("test@user.nl");

        // Act
        Recipe updated = recipeService.updateRecipe(1L, dto, "test@user.nl");

        // Assert
        assertEquals("Nieuwe titel", updated.getTitle());
        assertEquals("Nieuwe beschrijving", updated.getDescription());
        assertEquals(List.of(HarvestMonth.AUG), updated.getHarvestMonth());
        assertEquals(CuisineType.VEGETARIAN, updated.getCuisineType());
        assertEquals(DishType.SWEET, updated.getDishType());
        assertEquals(List.of("Stap 1", "Stap 2"), updated.getCookingSteps());
    }

    @Test
    @DisplayName("Should throw exception when recipe id for update not found")
    void updateRecipeThrowsWhenNotFound() {
        when(recipeRepository.findById(999L)).thenReturn(Optional.empty());

        RecipeRequestDto dto = new RecipeRequestDto();

        assertThrows(ResourceNotFoundException.class, () -> {
            recipeService.updateRecipe(999L, dto, "test@user.nl");
        });
    }

    @Test
    @DisplayName("Should throw exception when if user wants to update a recipe he/she/they has not created")
    void updateRecipeThrowsWhenUserNotCreator() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(createdByMock.getEmail()).thenReturn("creator@user.nl");

        RecipeRequestDto dto = new RecipeRequestDto();

        assertThrows(AccessDeniedException.class, () -> {
            recipeService.updateRecipe(1L, dto, "otheruser@user.nl");
        });
        verify(recipeRepository, never()).save(recipe);
    }

    @Test
    @DisplayName("Should delete recipe with id 1 if recipe is created by user")
    void deleteRecipeById() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(createdByMock.getEmail()).thenReturn("test@user.nl");

        recipeService.deleteRecipeById(1L, "test@user.nl");

        verify(recipeRepository).delete(recipe);
    }

    @Test
    @DisplayName("Should not delete recipe if user is not authorized")
    void deleteRecipeByIdThrowsException() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(createdByMock.getEmail()).thenReturn("test@user.nl");

        assertThrows(AccessDeniedException.class, () -> {
            recipeService.deleteRecipeById(1L, "wrongtest@user.nl");
        });

        verify(recipeRepository, never()).delete(recipe);
    }
}
