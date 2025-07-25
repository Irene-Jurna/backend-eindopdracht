package org.example.backend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.models.Ingredient;
import org.example.backend.models.StoreIngredient;
import org.example.backend.repositories.IngredientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IngredientUsageControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    // Repository is nodig om testdata aan te maken. We kunnen niet direct een IngredientUsage aanmaken, omdat die gebaseerd is op een IngredientId. Die moet dus eerst aangemaakt worden
    @Autowired
    IngredientRepository ingredientRepository;

    @Test
    @DisplayName("Should create correct IngredientUsage")
    void createIngredientUsage() throws Exception {

        // We maken een Ingredient-object aan in de test-database
        Ingredient ingredient = new StoreIngredient();
        ingredient.setName("test ingredient");
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        Long ingredientId = savedIngredient.getId();

        // requestJson is aangepast, zodat de opgeslagen ingredientId meegegeven kan worden
        String requestJson = String.format("""
            {
                "ingredientId" : %d,
                "quantity" : 200,
                "unit" :  "grammes"
            }
            """, ingredientId);

        // Dit is een kopie van het lesvoorbeeld
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/ingredient-usage")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // createdId uit de JSON-response halen
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(result.getResponse().getContentAsString());
        String createdId = jsonNode.get("id").asText();

        // Location veld in headers checken met de Hamcrest regex matcher (naar lesvoorbeeld)
        assertThat(result.getResponse().getHeader("Location"), matchesPattern("^.*/ingredient-usage/" + createdId));

    }
}
