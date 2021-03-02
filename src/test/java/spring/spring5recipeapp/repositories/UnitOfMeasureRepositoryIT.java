package spring.spring5recipeapp.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.spring5recipeapp.domain.UnitOfMeasure;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UnitOfMeasureRepositoryIT {
    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByDescription() {
        final String teaSpoon = "Teaspoon";
        final Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription(teaSpoon);
        assertEquals(teaSpoon, unitOfMeasureOptional.get().getDescription());
    }

    @Test
    public void findByDescriptionCup() {

        final String cup = "Cup";
        final Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription(cup);

        assertEquals(cup, uomOptional.get().getDescription());
    }
}