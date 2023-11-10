package com.tfg.mariomh.v2.myApi.mappers;

import com.tfg.mariomh.v2.myApi.mappers.implementations.MiniGameMapperImpl;
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
public class MiniGameMapperImplTest {
    private final static String WORD = "word";
    private final static String SYNONYM = "synonym";
    private final static String SIMILAR_WORD_1 = "similar1";
    private final static String SIMILAR_WORD_2 = "similar2";
    private final static String SIMILAR_WORD_3 = "similar3";
    @InjectMocks
    private MiniGameMapperImpl miniGameMapper;

    @BeforeEach
    public void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void gameToSynonymMiniGame(){
        List<SynonymSingle> synonymResult = miniGameMapper.gameToSynonymMiniGame(game());
        Assertions.assertEquals(synonymMiniGame().size(), synonymResult.size());
        for (int i=0; i<synonymMiniGame().size(); i++) {
            Assertions.assertEquals(synonymMiniGame().get(i).getWord(), synonymResult.get(i).getWord());
            Assertions.assertEquals(synonymMiniGame().get(i).getSynonym(), synonymResult.get(i).getSynonym());
        }
    }
    @Test
    public void gameToPhoneticMiniGame(){
        List<PhoneticSingle> phoneticResult = miniGameMapper.gameToPhoneticMiniGame(game());
        Assertions.assertEquals(phoneticMiniGame().size(), phoneticResult.size());
        for (int i=0; i<phoneticMiniGame().size(); i++) {
            Assertions.assertEquals(phoneticMiniGame().get(i).getWord(), phoneticResult.get(i).getWord());
            Assertions.assertEquals(phoneticMiniGame().get(i).getPhonetic(), phoneticResult.get(i).getPhonetic());
        }
    }
    @Test
    public void gameToDefinitionMiniGame(){
        List<DefinitionSingle> definitionResult = miniGameMapper.gameToDefinitionMiniGame(game());
        Assertions.assertEquals(definitionMiniGame().size(), definitionResult.size());
        for (int i=0; i<definitionMiniGame().size(); i++) {
            Assertions.assertEquals(definitionMiniGame().get(i).getWord(), definitionResult.get(i).getWord());
            Assertions.assertEquals(definitionResult.get(i).getDefinition(), definitionResult.get(i).getDefinition());
        }
    }
    private List<SynonymSingle> synonymMiniGame(){
        return List.of(new SynonymSingle(WORD, SYNONYM), new SynonymSingle(SIMILAR_WORD_1, SIMILAR_WORD_1), new SynonymSingle(SIMILAR_WORD_2, SIMILAR_WORD_2), new SynonymSingle(SIMILAR_WORD_3, SIMILAR_WORD_3));
    }
    private List<PhoneticSingle> phoneticMiniGame(){
        return List.of(phoneticSingle(WORD), phoneticSingle(SIMILAR_WORD_1), phoneticSingle(SIMILAR_WORD_2), phoneticSingle(SIMILAR_WORD_3));
    }
    private List<DefinitionSingle> definitionMiniGame(){
        return List.of(definitionSingle(WORD), definitionSingle(SIMILAR_WORD_1), definitionSingle(SIMILAR_WORD_2), definitionSingle(SIMILAR_WORD_3));
    }
    private Game game(){
        Game game = new Game();
        game.setId("id");
        game.setWord(WORD);
        game.setDate("19980701");
        game.setPhonetic(phonetic());
        game.setDefinition(definition());
        game.setSynonym(SYNONYM);
        game.setSimilarPhonetics(List.of(phoneticSingle(SIMILAR_WORD_1), phoneticSingle(SIMILAR_WORD_2), phoneticSingle(SIMILAR_WORD_3)));
        game.setSimilarDefinitions(List.of(definitionSingle(SIMILAR_WORD_1), definitionSingle(SIMILAR_WORD_2), definitionSingle(SIMILAR_WORD_3)));
        game.setSimilarSynonyms(List.of(SIMILAR_WORD_1, SIMILAR_WORD_2, SIMILAR_WORD_3));
        return game;
    }

    private Phonetic phonetic(){
        return new Phonetic("word.mp3", (short)1);
    }
    private Definition definition(){
        return new Definition(PartOfSpeech.NOUN, "definition of word");
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
