package spring.spring5recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.spring5recipeapp.commands.IngredientCommand;
import spring.spring5recipeapp.commands.RecipeCommand;
import spring.spring5recipeapp.services.IngredientService;
import spring.spring5recipeapp.services.RecipeService;
import spring.spring5recipeapp.services.UnitOfMeasureService;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID = 2L;

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new IngredientController(recipeService, ingredientService, unitOfMeasureService))
                .build();
    }

    @Test
    @DisplayName("test list of ingredients")
    void testListOfIngredients() throws Exception {
        when(recipeService.findCommandByID(anyLong())).thenReturn(new RecipeCommand());

        mockMvc.perform(get("/recipe/" + RECIPE_ID + "/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandByID(anyLong());
    }

    @Test
    @DisplayName("test show ingredient")
    void testShowIngredient() throws Exception {
        final IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/" + RECIPE_ID + "/ingredient/" + INGREDIENT_ID + "/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    @DisplayName("test new ingredient form")
    void testNewIngredientForm() throws Exception {
        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);

        when(recipeService.findCommandByID(anyLong())).thenReturn(recipeCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/" + RECIPE_ID + "/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(recipeService, times(1)).findCommandByID(anyLong());
    }


    @Test
    @DisplayName("test update ingredient form")
    void testUpdateIngredientForm() throws Exception {
        final IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    @DisplayName("test save or update ingredient")
    void testSaveOrUpdateIngredient() throws Exception {
        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(RECIPE_ID);
        ingredientCommand.setId(INGREDIENT_ID);

        when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);

        mockMvc.perform(post("/recipe/" + RECIPE_ID + "/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("ingredientID", "")
                .param("description", "some description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + RECIPE_ID + "/ingredient/" + INGREDIENT_ID + "/show"));
    }

    @Test
    @DisplayName("test delete ingredient")
    void testDeleteIngredient() throws Exception {
        mockMvc.perform(get("/recipe/" + RECIPE_ID + "/ingredient/" + INGREDIENT_ID + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + RECIPE_ID + "/ingredients"));

        verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());
    }
}
