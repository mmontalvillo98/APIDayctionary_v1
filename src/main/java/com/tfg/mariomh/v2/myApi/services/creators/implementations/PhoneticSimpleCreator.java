package com.tfg.mariomh.v2.myApi.services.creators.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.creation.PhoneticAudioCreationError;
import com.tfg.mariomh.v2.myApi.exceptions.creation.PhoneticCreationError;
import com.tfg.mariomh.v2.myApi.exceptions.creation.PhoneticSyllablesCreationError;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiEntryDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.models.entities.Phonetic;
import com.tfg.mariomh.v2.myApi.models.entities.Word;
import com.tfg.mariomh.v2.myApi.services.creators.interfaces.ISimpleCreator;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import org.springframework.stereotype.Service;

@Service
public class PhoneticSimpleCreator implements ISimpleCreator<Phonetic> {
    @Override
    public Phonetic find(DictionaryApiEntryDTO entry) throws PhoneticCreationError {
        return new Phonetic(findAudio(entry), findSyllables(entry));
    }
    @Override
    public Phonetic findAccordingTo(Game game, DictionaryApiEntryDTO entry) throws PhoneticCreationError {
        Short syllables = findSyllables(entry);
        if(similarSyllables(game.getPhonetic().getSyllables(), syllables)){
            return new Phonetic(findAudio(entry), syllables);
        }
        throw new PhoneticSyllablesCreationError(entry.getWord());
    }

    private String findAudio(DictionaryApiEntryDTO entry) throws PhoneticAudioCreationError {
        return entry.getPhonetics().stream()
                .map(Phonetic::getAudio)
                .filter(Validator::objectValid)
                .findFirst()
                .orElseThrow(()-> new PhoneticAudioCreationError(entry.getWord()));
    }

    // https://stackoverflow.com/questions/405161/detecting-syllables-in-a-word
    private Short findSyllables(Word wordObject) {
        String word = wordObject.getWord();
        char[] vowels = { 'a', 'e', 'i', 'o', 'u', 'y' };
        char[] currentWord = word.toCharArray();
        Short numVowels = 0;
        boolean lastWasVowel = false;
        for (char wc : currentWord) {
            boolean foundVowel = false;
            for (char v : vowels)
            {
                //don't count diphthongs
                if ((v == wc) && lastWasVowel)
                {
                    foundVowel = true;
                    lastWasVowel = true;
                    break;
                }
                else if (v == wc && !lastWasVowel)
                {
                    numVowels++;
                    foundVowel = true;
                    lastWasVowel = true;
                    break;
                }
            }
            // If full cycle and no vowel found, set lastWasVowel to false;
            if (!foundVowel)
                lastWasVowel = false;
        }
        // Remove es, it's _usually? silent
        if (word.length() > 2 &&
                word.substring(word.length() - 2) == "es")
            numVowels--;
            // remove silent e
        else if (word.length() > 1 &&
                word.substring(word.length() - 1) == "e")
            numVowels--;
        return numVowels;
    }

    private Boolean similarSyllables(Short parentSyllables, Short childSyllables){
        return parentSyllables==childSyllables
                || (parentSyllables-1>0 && parentSyllables-1==childSyllables)
                || parentSyllables+1==childSyllables;
    }

}
