package com.tfg.mariomh.v2.myApi.mappers;

import com.tfg.mariomh.v2.myApi.mappers.implementations.GameMapperImpl;
import com.tfg.mariomh.v2.myApi.models.dtos.GameDTO;
import com.tfg.mariomh.v2.myApi.models.entities.*;
import com.tfg.mariomh.v2.myApi.types.PartOfSpeech;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GameMapperImplTest {
    private final static String WORD = "word";
    private final static String WORD_AUDIO = "word.mp3";
    private final static String DATE = "19980701";
    private final static String SYNONYM = "synonym";
    private final static String SIMILAR_WORD_1 = "similar1";
    private final static String SIMILAR_WORD_2 = "similar2";
    private final static String SIMILAR_WORD_3 = "similar3";
    private final static short SYLLABLES = (short)1;
    private final static String DEFINITION = "This is a definition";
    @InjectMocks
    private GameMapperImpl gameMapper;

    @BeforeEach
    public void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void gameDtoToGame(){
        Game response = gameMapper.gameDtoToGame(gameDTO());
        Game expected = game();
        Assertions.assertEquals(expected.getWord(), response.getWord());
        Assertions.assertEquals(expected.getDate(), response.getDate());
        Assertions.assertEquals(expected.getSynonym(), response.getSynonym());
        Assertions.assertEquals(expected.getPhonetic(), response.getPhonetic());
        Assertions.assertEquals(expected.getDefinition(), response.getDefinition());
        Assertions.assertEquals(expected.getSimilarDefinitions().size(), response.getSimilarDefinitions().size());
        for (int i=0; i<expected.getSimilarDefinitions().size(); i++) {
            Assertions.assertEquals(expected.getSimilarDefinitions().get(i).getWord(), response.getSimilarDefinitions().get(i).getWord());
            Assertions.assertEquals(expected.getSimilarDefinitions().get(i).getDefinition(), response.getSimilarDefinitions().get(i).getDefinition());
        }
        Assertions.assertEquals(expected.getSimilarSynonyms().size(), response.getSimilarSynonyms().size());
        for (int i=0; i<expected.getSimilarSynonyms().size(); i++) {
            Assertions.assertEquals(expected.getSimilarSynonyms().get(i), response.getSimilarSynonyms().get(i));
        }
        Assertions.assertEquals(expected.getSimilarPhonetics().size(), response.getSimilarPhonetics().size());
        for (int i=0; i<expected.getSimilarPhonetics().size(); i++) {
            Assertions.assertEquals(expected.getSimilarPhonetics().get(i).getWord(), response.getSimilarPhonetics().get(i).getWord());
            Assertions.assertEquals(expected.getSimilarPhonetics().get(i).getPhonetic(), response.getSimilarPhonetics().get(i).getPhonetic());
        }

    }

    private GameDTO gameDTO(){
        GameDTO gameDTO = new GameDTO();
        gameDTO.setWord(WORD);
        gameDTO.setDate(DATE);
        gameDTO.setSimDfText0(DEFINITION);
        gameDTO.setSimDfText1(DEFINITION);
        gameDTO.setSimDfText2(DEFINITION);
        gameDTO.setSimDfText3(DEFINITION);
        gameDTO.setSimDfType0(PartOfSpeech.NOUN);
        gameDTO.setSimDfType1(PartOfSpeech.NOUN);
        gameDTO.setSimDfType2(PartOfSpeech.NOUN);
        gameDTO.setSimDfType3(PartOfSpeech.NOUN);
        gameDTO.setSimDfWord0(WORD);
        gameDTO.setSimDfWord1(SIMILAR_WORD_1);
        gameDTO.setSimDfWord2(SIMILAR_WORD_2);
        gameDTO.setSimDfWord3(SIMILAR_WORD_3);
        gameDTO.setSimPhAudio0(WORD_AUDIO);
        gameDTO.setSimPhAudio1(WORD_AUDIO);
        gameDTO.setSimPhAudio2(WORD_AUDIO);
        gameDTO.setSimPhAudio3(WORD_AUDIO);
        gameDTO.setSimPhSyllables0(SYLLABLES);
        gameDTO.setSimPhSyllables1(SYLLABLES);
        gameDTO.setSimPhSyllables2(SYLLABLES);
        gameDTO.setSimPhSyllables3(SYLLABLES);
        gameDTO.setSimPhWord0(WORD);
        gameDTO.setSimPhWord1(SIMILAR_WORD_1);
        gameDTO.setSimPhWord2(SIMILAR_WORD_2);
        gameDTO.setSimPhWord3(SIMILAR_WORD_3);
        gameDTO.setSimSyWord0(SYNONYM);
        gameDTO.setSimSyWord1(SIMILAR_WORD_1);
        gameDTO.setSimSyWord2(SIMILAR_WORD_2);
        gameDTO.setSimSyWord3(SIMILAR_WORD_3);
        return gameDTO;
    }
    private Game game(){
        Game game = new Game();
        game.setWord(WORD);
        game.setDate(DATE);
        game.setPhonetic(phonetic());
        game.setDefinition(definition());
        game.setSynonym(SYNONYM);
        game.setSimilarPhonetics(List.of(phoneticSingle(SIMILAR_WORD_1), phoneticSingle(SIMILAR_WORD_2), phoneticSingle(SIMILAR_WORD_3)));
        game.setSimilarDefinitions(List.of(definitionSingle(SIMILAR_WORD_1), definitionSingle(SIMILAR_WORD_2), definitionSingle(SIMILAR_WORD_3)));
        game.setSimilarSynonyms(List.of(SIMILAR_WORD_1, SIMILAR_WORD_2, SIMILAR_WORD_3));
        return game;
    }

    private Phonetic phonetic(){
        return new Phonetic(WORD_AUDIO, SYLLABLES);
    }
    private Definition definition(){
        return new Definition(PartOfSpeech.NOUN, DEFINITION);
    }
    private DefinitionSingle definitionSingle(String word){
        DefinitionSingle definitionSingle = new DefinitionSingle();
        definitionSingle.setWord(word);
        definitionSingle.setDefinition(definition());
        return definitionSingle;
    }
    private PhoneticSingle phoneticSingle(String word){
        PhoneticSingle phoneticSingle = new PhoneticSingle();
        phoneticSingle.setWord(word);
        phoneticSingle.setPhonetic(phonetic());
        return phoneticSingle;
    }

}
