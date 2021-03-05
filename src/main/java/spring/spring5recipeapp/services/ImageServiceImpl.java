package spring.spring5recipeapp.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public void saveImageFile(final Long recipeId, final MultipartFile file) {
        // FIXME
        log.debug("File: {} received for recipe id: {}", file, recipeId);
    }
}
