package spring.spring5recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.spring5recipeapp.commands.RecipeCommand;
import spring.spring5recipeapp.services.RecipeService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
    private static final Long ID_VALUE = 1L;
    @Mock
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("test list of ingredients")
    void testListOfIngredients() throws Exception {
        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new IngredientController(recipeService)).build();
        when(recipeService.findCommandByID(anyLong())).thenReturn(new RecipeCommand());

        mockMvc.perform(get("/recipe/" + ID_VALUE + "/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandByID(anyLong());
    }
}
