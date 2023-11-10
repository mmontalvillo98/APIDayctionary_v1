package com.tfg.mariomh.v2.myApi.services.bbdd;

import com.tfg.mariomh.v2.myApi.exceptions.responses.GameImposibleDateResponseException;
import com.tfg.mariomh.v2.myApi.exceptions.responses.GameRepeatedOnMiniGameResponseException;
import com.tfg.mariomh.v2.myApi.exceptions.responses.ObjectWithoutParamResponseException;
import com.tfg.mariomh.v2.myApi.models.daos.GameDao;
import com.tfg.mariomh.v2.myApi.models.entities.*;
import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.GameServiceImpl;
import com.tfg.mariomh.v2.myApi.types.PartOfSpeech;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    private final static String GAME_WORD = "word";
    private final static String GAME_DATE = "20230902";
    private final static String GAME_SYNONYM = "synonym";
    private final static String GAME_AUDIO = "audio.mp3";
    private final static String GAME_DEFINITION = "This is a definition";
    private final static PartOfSpeech GAME_TYPE = PartOfSpeech.NOUN;
    private final static Short GAME_SYLLABLES = 1;
    private final static String EMPTY_STRING = "";
    private final static String JUST_SPACES = "      ";

    @InjectMocks
    private GameServiceImpl gameService;
    @Mock
    private GameDao gameDao;

    @BeforeEach
    public void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validateGame(){
        Game game = new Game();
        game.setWord(GAME_WORD);
        game.setDate(GAME_DATE);
        game.setSynonym(GAME_SYNONYM);
        game.setDefinition(new Definition(GAME_TYPE, GAME_DEFINITION));
        game.setPhonetic(new Phonetic(GAME_AUDIO, GAME_SYLLABLES));
        game.setSimilarSynonyms(List.of("a", "b", "c"));
        game.setSimilarPhonetics(List.of(
                new PhoneticSingle("a", new Phonetic("a", (short)1)),
                new PhoneticSingle("b", new Phonetic("b", (short)1)),
                new PhoneticSingle("c", new Phonetic("c", (short)1))
        ));
        game.setSimilarDefinitions(List.of(
                new DefinitionSingle("a", new Definition(PartOfSpeech.NOUN, "a")),
                new DefinitionSingle("b", new Definition(PartOfSpeech.NOUN, "b")),
                new DefinitionSingle("c", new Definition(PartOfSpeech.NOUN, "c"))
        ));
        assertDoesNotThrow(()-> gameService.validate(game));
    }

    @Test
    public void validateWordTest(){
        Game game = new Game();
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateWord(game.getWord()));
        game.setWord(EMPTY_STRING);
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateWord(game.getWord()));
        game.setWord(JUST_SPACES);
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateWord(game.getWord()));
        game.setWord(GAME_WORD);
        assertDoesNotThrow(()-> gameService.validateWord(game.getWord()));
    }

    @Test
    public void validateDateTest(){
        Game game = new Game();
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateDate(game.getDate()));
        game.setDate(GAME_DATE);
        // A value with GAME_DATE doesn't exist:
        assertDoesNotThrow(()-> gameService.validateWord(game.getDate()));
        // A value with GAME_DATE exists:
        Mockito.doReturn(Optional.of(new Game())).when(gameDao).findByDate(Mockito.anyString());
        assertThrows(GameImposibleDateResponseException.class, ()-> gameService.validateDate(game.getDate()));
    }

    @Test
    public void validatePhoneticTest(){
        Game game = new Game();
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validatePhonetic(game.getPhonetic()));
        game.setPhonetic(new Phonetic());
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validatePhonetic(game.getPhonetic()));
        game.getPhonetic().setAudio(null);
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validatePhonetic(game.getPhonetic()));
        game.getPhonetic().setAudio("");
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validatePhonetic(game.getPhonetic()));
        game.getPhonetic().setAudio("  ");
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validatePhonetic(game.getPhonetic()));
        game.getPhonetic().setAudio(GAME_AUDIO);
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validatePhonetic(game.getPhonetic()));
        game.getPhonetic().setSyllables((short)-1);
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validatePhonetic(game.getPhonetic()));
        game.getPhonetic().setSyllables((short)0);
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validatePhonetic(game.getPhonetic()));
        game.getPhonetic().setSyllables(GAME_SYLLABLES);
        assertDoesNotThrow(()-> gameService.validatePhonetic(game.getPhonetic()));
    }

    @Test
    public void validatePhoneticMiniGameTest(){
        Game game = new Game();
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validatePhoneticMiniGame(game.getPhoneticMiniGame()));
        game.setWord(GAME_WORD);
        game.setPhonetic(new Phonetic(GAME_AUDIO, GAME_SYLLABLES));
        game.setSimilarPhonetics(List.of(
                new PhoneticSingle("a", new Phonetic("a", (short)1)),
                new PhoneticSingle("b", new Phonetic("b", (short)1)),
                new PhoneticSingle("c", new Phonetic("c", (short)1))
        ));
        assertDoesNotThrow(()-> gameService.validatePhoneticMiniGame(game.getPhoneticMiniGame()));
    }

    @Test
    public void validateDefinitionMiniGameTest(){
        Game game = new Game();
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateDefinitionMiniGame(game.getDefinitionMiniGame()));
        game.setSimilarDefinitions(List.of(
                new DefinitionSingle("a", new Definition(PartOfSpeech.NOUN, "a")),
                new DefinitionSingle("b", new Definition(PartOfSpeech.NOUN, "b")),
                new DefinitionSingle("c", new Definition(PartOfSpeech.NOUN, "c"))
        ));
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateDefinitionMiniGame(game.getDefinitionMiniGame()));
        game.setDefinition(new Definition(GAME_TYPE, GAME_DEFINITION));
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateDefinitionMiniGame(game.getDefinitionMiniGame()));
        game.setWord(GAME_WORD);
        assertDoesNotThrow(()-> gameService.validateDefinitionMiniGame(game.getDefinitionMiniGame()));
    }

    @Test
    public void validateSynonymMiniGameTest(){
        Game game = new Game();
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateSynonymMiniGame(game.getSynonymMiniGame()));
        game.setSynonym(GAME_SYNONYM);
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateSynonymMiniGame(game.getSynonymMiniGame()));
        game.setSimilarSynonyms(List.of("similar1", "similar2"));
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateSynonymMiniGame(game.getSynonymMiniGame()));
        game.setSimilarSynonyms(List.of("similar1", "similar2", "similar3", "similar4"));
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateSynonymMiniGame(game.getSynonymMiniGame()));
        game.setSimilarSynonyms(List.of("similar1", "similar3", "similar3"));
        assertThrows(GameRepeatedOnMiniGameResponseException.class, ()-> gameService.validateSynonymMiniGame(game.getSynonymMiniGame()));
        game.setSimilarSynonyms(List.of("similar1", GAME_SYNONYM, "similar3"));
        assertThrows(GameRepeatedOnMiniGameResponseException.class, ()-> gameService.validateSynonymMiniGame(game.getSynonymMiniGame()));
        game.setSimilarSynonyms(List.of("similar1", "similar2", "similar3"));
        assertThrows(GameRepeatedOnMiniGameResponseException.class, ()-> gameService.validateSynonymMiniGame(game.getSynonymMiniGame()));
        game.setSimilarSynonyms(List.of("a", "b", "c"));
        assertDoesNotThrow(()-> gameService.validateSynonymMiniGame(game.getSynonymMiniGame()));
    }


    @Test
    public void validateDefinitionTest(){
        Game game = new Game();
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateDefinition(game.getDefinition()));
        game.setDefinition(new Definition());
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateDefinition(game.getDefinition()));
        game.getDefinition().setText("");
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateDefinition(game.getDefinition()));
        game.getDefinition().setText("   ");
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateDefinition(game.getDefinition()));
        game.getDefinition().setText(GAME_DEFINITION);
        assertThrows(ObjectWithoutParamResponseException.class, ()-> gameService.validateDefinition(game.getDefinition()));
        game.getDefinition().setType(GAME_TYPE);
        assertDoesNotThrow(()-> gameService.validateDefinition(game.getDefinition()));
    }

}
