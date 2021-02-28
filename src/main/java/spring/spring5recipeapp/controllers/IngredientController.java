package spring.spring5recipeapp.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.spring5recipeapp.services.RecipeService;

@Log4j2
@Controller
public class IngredientController {
    private final RecipeService recipeService;

    public IngredientController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String showIngredients(@PathVariable final String recipeId, final Model model) {
        log.debug("Getting ingredient list for recipe id: {}", recipeId);
        model.addAttribute("recipe", recipeService.findCommandByID(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }
}
