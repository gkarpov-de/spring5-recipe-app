package spring.spring5recipeapp.converters;


import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import spring.spring5recipeapp.commands.CategoryCommand;
import spring.spring5recipeapp.domain.Category;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(@Nullable final CategoryCommand categoryCommand) {
        if (categoryCommand == null) {
            return null;
        }
        final Category category = new Category();
        category.setId(categoryCommand.getId());
        category.setDescription(categoryCommand.getDescription());
        return category;
    }
}
