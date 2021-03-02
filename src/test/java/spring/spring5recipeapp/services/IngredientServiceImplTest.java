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
    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID = 2L;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    @Mock
    private RecipeRepository recipeRepository;
    private IngredientService ingredientService;
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
    public void findByRecipeIdAndId() {
    }

    @Test
    @DisplayName("test find by recipe id and id")
    void testFindByRecipeIdAndIdHappyPath() {
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

    @Test
    @DisplayName("test save RecipeCommand")
    void testSaveRecipeCommand() {
        // given
        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(RECIPE_ID);
        ingredientCommand.setId(INGREDIENT_ID);

        final Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        final Recipe savedRecipe = new Recipe();
        savedRecipe.setId(RECIPE_ID);
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(INGREDIENT_ID);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        final IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //then
        assertEquals(RECIPE_ID, savedCommand.getRecipeId());
        assertEquals(INGREDIENT_ID, savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    @DisplayName("test delete Ingredient")
    void testDeleteIngredient() {
        // given
        final Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        recipe.addIngredient(ingredient);
        final Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        // when
        ingredientService.deleteById(RECIPE_ID, INGREDIENT_ID);

        // then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}