package spring.spring5recipeapp.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.spring5recipeapp.commands.IngredientCommand;
import spring.spring5recipeapp.services.IngredientService;
import spring.spring5recipeapp.services.RecipeService;
import spring.spring5recipeapp.services.UnitOfMeasureService;

@Log4j2
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(final RecipeService recipeService, final IngredientService ingredientService, final UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String showIngredients(@PathVariable final String recipeId, final Model model) {
        log.debug("Getting ingredient list for recipe id: {}", recipeId);
        model.addAttribute("recipe", recipeService.findCommandByID(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable final String recipeId, @PathVariable final String ingredientId, final Model model) {
        log.debug("Getting ingredient id: {} from recipe id: {}", ingredientId, recipeId);
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable final String recipeId, @PathVariable final String ingredientId, final Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute final IngredientCommand ingredientCommand) {
        final IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        final Long ingredientCommandId = savedIngredientCommand.getId();
        final Long recipeId = savedIngredientCommand.getRecipeId();
        log.debug("saved ingredient id: {}, recipe id: {}", ingredientCommandId, recipeId);

        return "redirect:/recipe/" + recipeId + "/ingredient/" + ingredientCommandId + "/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable final String recipeId, @PathVariable final String ingredientId) {
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
