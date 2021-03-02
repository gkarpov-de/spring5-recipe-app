package spring.spring5recipeapp.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.spring5recipeapp.commands.IngredientCommand;
import spring.spring5recipeapp.converters.IngredientCommandToIngredient;
import spring.spring5recipeapp.converters.IngredientToIngredientCommand;
import spring.spring5recipeapp.domain.Ingredient;
import spring.spring5recipeapp.domain.Recipe;
import spring.spring5recipeapp.repositories.RecipeRepository;
import spring.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Log4j2
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(final IngredientCommand ingredientCommand) {
        final Long recipeId = ingredientCommand.getRecipeId();
        final Long ingredientCommandId = ingredientCommand.getId();
        final Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            log.debug("Recipe with id: {} not found.", recipeId);
            return new IngredientCommand();
        }
        final Recipe recipe = recipeOptional.get();

        final Optional<Ingredient> optionalIngredient = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommandId))
                .findFirst();

        if (optionalIngredient.isPresent()) {
            final Ingredient ingredientFound = optionalIngredient.get();
            ingredientFound.setDescription(ingredientCommand.getDescription());
            ingredientFound.setAmount(ingredientCommand.getAmount());
            ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("<DB Error> Uom not found."))); // TODO who/how will catch it?!
        } else {
            final Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        final Recipe savedRecipe = recipeRepository.save(recipe);

        final Ingredient savedIngredient = savedRecipe.getIngredients()
                .stream()
                .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommandId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("<Server error> Could not save ingredient."));

        return ingredientToIngredientCommand.convert(savedIngredient);
    }

    @Override
    public void deleteById(final Long recipeId, final Long ingredientId) {
        log.debug("Deleting ingredient id: {} from recipe id: {}");
        final Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            log.error("<deleteById> Recipe id: {} not found.", recipeId);
            return;
        }

        log.debug("recipe id: {} found", recipeId);
        final Recipe recipe = recipeOptional.get();

        final Optional<Ingredient> optionalIngredient = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        if (optionalIngredient.isEmpty()) {
            log.error("<deleteById> Ingredient id: {} not found", ingredientId);
            return;
        }

        log.debug("Ingredient id: {} found", ingredientId);
        final Ingredient ingredientToDelete = optionalIngredient.get();

        ingredientToDelete.setRecipe(null);
        recipe.getIngredients().remove(ingredientToDelete);
        recipeRepository.save(recipe);
    }


}
