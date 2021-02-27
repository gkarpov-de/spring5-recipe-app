package spring.spring5recipeapp.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import spring.spring5recipeapp.commands.RecipeCommand;
import spring.spring5recipeapp.converters.RecipeCommandToRecipe;
import spring.spring5recipeapp.converters.RecipeToRecipeCommand;
import spring.spring5recipeapp.domain.Recipe;
import spring.spring5recipeapp.repositories.RecipeRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(final RecipeRepository recipeRepository, final RecipeCommandToRecipe recipeCommandToRecipe, final RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Executing getRecipe");
        final Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findByID(final long l) {
        log.debug("Executing findByID({})", l);
        final Optional<Recipe> recipeOptional = recipeRepository.findById(l);
        if (recipeOptional.isEmpty()) {
            throw new RuntimeException("Recipe with id:" + l + " not found!");
        }
        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(final RecipeCommand command) {
        final Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        final Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved Recipe id: {}", savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }
}
