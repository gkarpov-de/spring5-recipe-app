package spring.spring5recipeapp.services;

import org.springframework.stereotype.Service;
import spring.spring5recipeapp.commands.IngredientCommand;
import spring.spring5recipeapp.converters.IngredientCommandToIngredient;
import spring.spring5recipeapp.converters.IngredientToIngredientCommand;
import spring.spring5recipeapp.domain.Recipe;
import spring.spring5recipeapp.repositories.RecipeRepository;
import spring.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    private RecipeRepository recipeRepository;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(final IngredientToIngredientCommand ingredientToIngredientCommand,
                                 final IngredientCommandToIngredient ingredientCommandToIngredient,
                                 final RecipeRepository recipeRepository,
                                 final UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(final Long recipeId, final Long id) {
        final Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            throw new RuntimeException("Recipe with id: " + recipeId + "not found");
        }

        final Optional<IngredientCommand> ingredientCommandOptional = recipeOptional.get()
                .getIngredients().stream().filter(ingredient -> ingredient.getId().equals(id))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if (ingredientCommandOptional.isEmpty()) {
            throw new RuntimeException("Ingredient with id: " + id + "not found");
        }

        return ingredientCommandOptional.get();
    }
}
