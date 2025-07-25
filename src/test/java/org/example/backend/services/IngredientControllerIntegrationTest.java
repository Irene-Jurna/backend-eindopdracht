package org.example.backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.security.MyUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.http.MediaType.APPLICATION_JSON;

// Het JwtRequestFilter wordt uitgezet, zodat we niet te maken hebben met Spring Security in deze integratietest
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class IngredientControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Should create correct Ingredient")
    void createIngredient() throws Exception {

        // Omdat de createIngredient() in de IngredientController een param heeft voor MyUserDetails, worden die in deze test gemockt
        MyUserDetails userDetailsMock = Mockito.mock(MyUserDetails.class);
        Mockito.when(userDetailsMock.getUsername()).thenReturn(("test@email.org"));

        // Onze bovenstaande mock-gebruiker moet wel 'ingelogd' zijn om de test te laten slagen (Spring verwacht een @AuthenticationPrinciple)
        // Daarom wordt de mock-user gekoppeld aan een token en in een SecurityContext gezet
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetailsMock, null, userDetailsMock.getAuthorities()));
        SecurityContextHolder.setContext(context);

        // Dit is de test-data die wordt meegestuurd in de requestbody
        String requestJson = """
                {
                    "type" : "STORE",
                    "name" : "Oersuiker",
                    "pointOfSale" : "Fruittuin van West"
                }
                """;

        // De request wordt uitgevoerd
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/ingredients")
                .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // Jackson's ObjectMapper zet de bovenstaande JSON om in een Java Object. Vervolgens lezen we het id uit de JSON en wordt die omgezet naar een string met asText()
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(result.getResponse().getContentAsString());
        String createdId = jsonNode.get("id").asText();

        // Check de location-URI van de response
        assertThat(result.getResponse().getHeader("Location"), matchesPattern("^.*/ingredients/" +  createdId));
    }
}
