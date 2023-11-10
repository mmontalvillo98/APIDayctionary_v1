package com.tfg.mariomh.v2.myApi.services.creators.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.creation.DefinitionCreationError;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiEntryDTO;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiMeaningDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Definition;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.services.creators.interfaces.ISimpleCreator;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import com.tfg.mariomh.v2.myApi.utils.WordValidator;
import org.springframework.stereotype.Service;

@Service
public class DefinitionSimpleCreator implements ISimpleCreator<Definition> {
    @Override
    public Definition find(DictionaryApiEntryDTO entry) throws DefinitionCreationError {
        for(DictionaryApiMeaningDTO meaning: entry.getMeanings()){
            Definition definition = findDefinitionOnMeaning(entry.getWord(), meaning);
            if(definition!=null){
                return definition;
            }
        }
        throw new DefinitionCreationError(entry.getWord());
    }

    @Override
    public Definition findAccordingTo(Game game, DictionaryApiEntryDTO entry) throws DefinitionCreationError {
        for(DictionaryApiMeaningDTO meaning: entry.getMeanings()){
            Definition definition = findDefinitionOnMeaningAccordingTo(game, entry.getWord(), meaning);
            if(definition!=null){
                return definition;
            }
        }
        throw new DefinitionCreationError(entry.getWord());
    }

    private Definition findDefinitionOnMeaning(String word, DictionaryApiMeaningDTO meaning) {
        return meaning.getDefinitions().stream()
                .map(dictionaryApiDefinition -> new Definition(meaning.getPartOfSpeech(), dictionaryApiDefinition.getDefinition()))
                .filter(definition-> Validator.objectsValid(definition.getType(), definition.getText()))
                .filter(definition -> !WordValidator.wordInPhrase(word, definition.getText()))
                .findFirst()
                .orElse(null);
    }

    private Definition findDefinitionOnMeaningAccordingTo(Game game, String word, DictionaryApiMeaningDTO meaning) {
        return meaning.getDefinitions().stream()
                .map(dictionaryApiDefinition -> new Definition(meaning.getPartOfSpeech(), dictionaryApiDefinition.getDefinition()))
                .filter(definition-> Validator.objectsValid(definition.getType(), definition.getText()))
                .filter(definition -> game.getDefinition().getType()==definition.getType())
                .filter(definition -> !WordValidator.wordInPhrase(word, definition.getText()))
                .filter(definition -> !WordValidator.wordInPhrase(game, definition.getText()))
                .findFirst()
                .orElse(null);
    }
}
