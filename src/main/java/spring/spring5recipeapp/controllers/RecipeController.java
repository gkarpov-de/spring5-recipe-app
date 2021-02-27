package spring.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.spring5recipeapp.domain.Recipe;
import spring.spring5recipeapp.services.RecipeService;

@Controller
public class RecipeController {
    RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/show/{id}")
    public String showByID(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findByID(new Long(id)));
        return "recipe/show";
    }
}
