package spring.spring5recipeapp.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.spring5recipeapp.commands.RecipeCommand;
import spring.spring5recipeapp.converters.RecipeCommandToRecipe;
import spring.spring5recipeapp.converters.RecipeToRecipeCommand;
import spring.spring5recipeapp.domain.Recipe;
import spring.spring5recipeapp.repositories.RecipeRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecipeServiceIT {
    private static final String NEW_DESCRIPTION = "New description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Transactional
    @Test
    @DisplayName("test save of description")
    void testSaveOfDescription() {
        final Iterable<Recipe> recipes = recipeRepository.findAll();
        final Recipe testRecipe = recipes.iterator().next();
        final RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        final RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
