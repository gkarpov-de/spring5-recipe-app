package spring.spring5recipeapp.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.spring5recipeapp.services.RecipeService;

@Log4j2
@Controller
public class IndexController {
    private final RecipeService recipeService;

    public IndexController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(final Model model) {
        log.debug("Getting Index page");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
