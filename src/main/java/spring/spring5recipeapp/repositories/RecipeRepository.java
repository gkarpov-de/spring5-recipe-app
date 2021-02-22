package spring.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.spring5recipeapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
