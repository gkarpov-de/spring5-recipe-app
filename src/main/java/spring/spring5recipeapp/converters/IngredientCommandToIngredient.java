package spring.spring5recipeapp.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import spring.spring5recipeapp.commands.IngredientCommand;
import spring.spring5recipeapp.domain.Ingredient;

public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if(ingredientCommand == null) {
        return null;}

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientCommand.getId());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setUom(unitOfMeasureCommandToUnitOfMeasure.convert(ingredientCommand.getUnitOfMeasureCommand()));
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setRecipe(ingredientCommand.getRecipe());
        return ingredient;
    }
}