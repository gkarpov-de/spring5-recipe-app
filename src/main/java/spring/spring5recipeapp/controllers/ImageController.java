package spring.spring5recipeapp.controllers;

import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import spring.spring5recipeapp.commands.RecipeCommand;
import spring.spring5recipeapp.services.ImageService;
import spring.spring5recipeapp.services.RecipeService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
@Controller
public class ImageController {
    ImageService imageService;
    RecipeService recipeService;

    public ImageController(final RecipeService recipeService, final ImageService imageService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{recipeId}/image")
    public String showUploadImageForm(@PathVariable final String recipeId, final Model model) {
        model.addAttribute("recipe", recipeService.findCommandByID(Long.valueOf(recipeId)));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable final String id, @RequestParam("imagefile") final MultipartFile file) {

        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{recipeId}/recipeimage")
    public void renderImage(@PathVariable final String recipeId, final HttpServletResponse response) throws IOException {
        final RecipeCommand recipeCommand = recipeService.findCommandByID(Long.valueOf(recipeId));

        final Byte[] image = recipeCommand.getImage();
        if (image == null) {
            return;
        }

        final byte[] bytes = new byte[image.length];

        int i = 0;
        for (final Byte aByte : image) {
            bytes[i++] = aByte;
        }

        response.setContentType("image/jpeg");
        final InputStream inputStream = new ByteArrayInputStream(bytes);
        IOUtils.copy(inputStream, response.getOutputStream());
    }


}
