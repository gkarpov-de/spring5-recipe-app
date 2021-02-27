package spring.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import spring.spring5recipeapp.commands.NotesCommand;
import spring.spring5recipeapp.domain.Notes;

public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {
    @Override
    public Notes convert(final NotesCommand notesCommand) {
        if (notesCommand == null) {
            return null;
        }

        final Notes notes = new Notes();
        notes.setId(notesCommand.getId());
        notes.setRecipeNotes(notesCommand.getRecipeNotes());
        return notes;
    }
}
