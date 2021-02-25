package spring.spring5recipeapp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spring.spring5recipeapp.repositories.CategoryRepository;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    Category category;

    @BeforeEach
    public void setUp(){
        category = new Category();
    }

    @Test
    void getId() {
        Long id = 1L;
        category.setId(id);
        assertEquals(id, category.getId());
    }

    @Test
    void getDescription() {
        String description = "some description";
        category.setDescription(description);
        assertEquals(description, category.getDescription());
    }

    @Test
    void getRecipe() {
    }
}