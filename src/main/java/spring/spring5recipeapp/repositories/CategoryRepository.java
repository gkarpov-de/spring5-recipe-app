package spring.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.spring5recipeapp.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
