package spring.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.spring5recipeapp.domain.Category;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByDescription(String desctription);
}
