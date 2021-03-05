package spring.spring5recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.spring5recipeapp.commands.RecipeCommand;
import spring.spring5recipeapp.services.ImageService;
import spring.spring5recipeapp.services.RecipeService;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {
    private static final Long RECIPE_ID = 1L;
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;

    ImageController imageController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageController = new ImageController(recipeService, imageService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    @DisplayName("open image form test")
    void openImageFormTest() throws Exception {
        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        when(recipeService.findCommandByID(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/" + RECIPE_ID + "/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandByID(anyLong());
    }


    @Test
    @DisplayName("handle image post test")
    void handleImagePostTest() throws Exception {
        final MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "test.txt", "text/plain", "Some strange text".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/recipe/" + RECIPE_ID + "/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/" + RECIPE_ID + "/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }

}
