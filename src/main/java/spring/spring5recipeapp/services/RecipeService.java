package spring.spring5recipeapp.services;

import spring.spring5recipeapp.domain.Recipe;

import java.util.Set;


public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findByID(long l);
}
