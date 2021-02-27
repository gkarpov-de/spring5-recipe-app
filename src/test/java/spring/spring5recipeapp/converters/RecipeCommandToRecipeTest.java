package spring.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spring.spring5recipeapp.commands.CategoryCommand;
import spring.spring5recipeapp.commands.IngredientCommand;
import spring.spring5recipeapp.commands.NotesCommand;
import spring.spring5recipeapp.commands.RecipeCommand;
import spring.spring5recipeapp.domain.Difficulty;
import spring.spring5recipeapp.domain.Recipe;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeCommandToRecipeTest {
    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = 1;
    public static final Integer PREP_TIME = 2;
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = 3;
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID1 = 2L;
    public static final Long CAT_ID2 = 3L;
    public static final Long INGREDIENT_ID1 = 4L;
    public static final Long INGREDIENT_ID2 = 5L;
    public static final Long NOTES_ID = 6L;

    RecipeCommandToRecipe converter;


    @BeforeEach
    public void setUp() {
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void testConversion() {
        //given
        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);

        final NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);
        recipeCommand.setNotes(notes);

        final CategoryCommand category1 = new CategoryCommand();
        category1.setId(CAT_ID1);
        final CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID2);
        recipeCommand.getCategories().add(category1);
        recipeCommand.getCategories().add(category2);

        final IngredientCommand ingredient1 = new IngredientCommand();
        ingredient1.setId(INGREDIENT_ID1);
        final IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGREDIENT_ID2);
        recipeCommand.getIngredients().add(ingredient1);
        recipeCommand.getIngredients().add(ingredient2);

        //when
        final Recipe recipe = converter.convert(recipeCommand);

        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }

}

