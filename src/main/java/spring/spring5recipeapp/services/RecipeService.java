package spring.spring5recipeapp.services;

import spring.spring5recipeapp.commands.RecipeCommand;
import spring.spring5recipeapp.domain.Recipe;

import java.util.Set;


public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findByID(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandByID(Long l);

    void deleteById(Long idValue);
}
