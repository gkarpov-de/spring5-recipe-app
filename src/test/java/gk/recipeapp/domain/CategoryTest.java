package gk.recipeapp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {
    public static final Long ID_VALUE = 1L;
    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        final Long id = ID_VALUE;
        category.setId(id);
        assertEquals(id, category.getId());
    }

    @Test
    void getDescription() {
        final String description = "some description";
        category.setDescription(description);
        assertEquals(description, category.getDescription());
    }

    @Test
    void getRecipes() {
    }
}