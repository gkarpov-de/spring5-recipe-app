package spring.spring5recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.spring5recipeapp.commands.IngredientCommand;
import spring.spring5recipeapp.converters.IngredientCommandToIngredient;
import spring.spring5recipeapp.converters.IngredientToIngredientCommand;
import spring.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import spring.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import spring.spring5recipeapp.domain.Ingredient;
import spring.spring5recipeapp.domain.Recipe;
import spring.spring5recipeapp.repositories.RecipeRepository;
import spring.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {
    @Mock
    private RecipeRepository recipeRepository;
    private IngredientService ingredientService;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeRepository, unitOfMeasureRepository);
    }

    @Test
    @DisplayName("test find by recipe id and id")
    void testFindByRecipeIdAndId() {
        final Recipe recipe = new Recipe();
        recipe.setId(1L);
        final Ingredient ingredient1 = new Ingredient();
        final Ingredient ingredient2 = new Ingredient();
        final Ingredient ingredient3 = new Ingredient();
        ingredient1.setId(1L);
        ingredient2.setId(2L);
        ingredient3.setId(3L);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        final Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        final IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 2L);
        assertEquals(1L, ingredientCommand.getRecipeId());
        assertEquals(2L, ingredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());

    }
}