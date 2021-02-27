package spring.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.spring5recipeapp.commands.NotesCommand;
import spring.spring5recipeapp.domain.Notes;

import static org.junit.jupiter.api.Assertions.*;

public class NotesToNotesCommandTest {
    private static final Long ID_VALUE = 1L;
    private static final String RECIPE_NOTES = "abrakadabra";

    NotesToNotesCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    @DisplayName("test null")
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    @DisplayName("test empty object")
    void testEmptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    @DisplayName("test conversion")
    void testConversion() {
        final Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        final NotesCommand notesCommand = converter.convert(notes);

        assertNotNull(notesCommand);
        assertEquals(ID_VALUE, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
    }
}
