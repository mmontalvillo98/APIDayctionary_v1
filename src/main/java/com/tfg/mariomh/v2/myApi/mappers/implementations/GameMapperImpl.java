package com.tfg.mariomh.v2.myApi.mappers.implementations;

import com.tfg.mariomh.v2.myApi.models.dtos.GameDTO;
import com.tfg.mariomh.v2.myApi.models.entities.*;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameMapperImpl {

    public Game gameDtoToGame(GameDTO source){
        Game target = new Game();
        if(source!=null){
            mapFieldsGameDTOToGame(source, target);
        }
        return target;
    }

    private void mapFieldsGameDTOToGame(GameDTO source, Game target){
        target.setWord(source.getWord());
        target.setDate(source.getDate());
        target.setPhonetic(new Phonetic(source.getSimPhAudio0(), source.getSimPhSyllables0()));
        target.setDefinition(new Definition(source.getSimDfType0(), source.getSimDfText0()));
        target.setSynonym(source.getSimSyWord0());
        target.setSimilarPhonetics(List.of(
                new PhoneticSingle(source.getSimPhWord1(), source.getSimPhAudio1(), source.getSimPhSyllables1()),
                new PhoneticSingle(source.getSimPhWord2(), source.getSimPhAudio2(), source.getSimPhSyllables2()),
                new PhoneticSingle(source.getSimPhWord3(), source.getSimPhAudio3(), source.getSimPhSyllables3())
                ));
        target.setSimilarDefinitions(List.of(
                new DefinitionSingle(source.getSimDfWord1(), source.getSimDfType1(), source.getSimDfText1()),
                new DefinitionSingle(source.getSimDfWord2(), source.getSimDfType2(), source.getSimDfText2()),
                new DefinitionSingle(source.getSimDfWord3(), source.getSimDfType3(), source.getSimDfText3())
                ));
        target.setSimilarSynonyms(List.of(
                source.getSimSyWord1(),
                source.getSimSyWord2(),
                source.getSimSyWord3())
        );
    }

}
