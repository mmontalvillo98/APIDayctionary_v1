package com.tfg.mariomh.v2.myApi.services.bbdd.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.creation.GameCreationError;
import com.tfg.mariomh.v2.myApi.exceptions.responses.*;
import com.tfg.mariomh.v2.myApi.mappers.implementations.GameMapperImpl;
import com.tfg.mariomh.v2.myApi.models.daos.GameDao;
import com.tfg.mariomh.v2.myApi.models.dtos.GameDTO;
import com.tfg.mariomh.v2.myApi.models.entities.*;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.services.bbdd.interfaces.IGameService;
import com.tfg.mariomh.v2.myApi.services.creators.GameCreator;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import com.tfg.mariomh.v2.myApi.utils.WordValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GameServiceImpl implements IGameService {

    private final GameDao gameDao;
    private final GameMapperImpl gameMapper;
    private final GameCreator gameCreator;

    /**
     * Valida que un juego es correcto y en ese caso lo guarda.
     * @param gameDTO juego nuevo
     * @return Game juego guardado
     */
    @Override
    public Game save(GameDTO gameDTO) {
        Game game = gameMapper.gameDtoToGame(gameDTO);
        validate(game);
        game.setAnswers(new ArrayList<>());
        return gameDao.save(game);
    }

    /**
     * Obtiene el juego completo de una fecha concreta
     * @param date fecha con formato YYYYMMDD
     * @return Game juego del día indicado
     */
    @Override
    public Game getByDate(String date) {
        try{
            return gameDao.findByDate(date)
                    .orElseThrow(()->new GameNotFoundWithDateResponseException(date));
        }catch(DateTimeException e){
            throw new GameImposibleDateResponseException(date);
        }
    }

    /**
     * Obtiene la palabra y el identificador del juego de una fecha concreta
     * @param date fecha con formato YYYYMMDD
     * @return Game objeto con la palabra y el identificador del juego del día indicado
     */
    @Override
    public Game getDateWord(String date) {
        try{
            return gameDao.findWordByDate(date).orElseThrow(()->new GameNotFoundWithDateResponseException(date));
        }catch(DateTimeException e){
            throw new GameImposibleDateResponseException(date);
        }
    }

    /**
     * Crea un juego aleatorio
     * @return Game juego creado
     */
    @Override
    public Game createRandomGame() {
        return gameCreator.create();
    }

    /**
     * Crea un juego a partir de una palabra concreta
     * @param word palabra sobre la que se quiere hacer un juego
     * @return Game juego creado
     */
    @Override
    public Game createGameFromWord(String word) {
        try{
            return gameCreator.createFromWord(word);
        } catch (GameCreationError e) {
            throw new GameCreationResponseException();
        }
    }

    public void validate(Game game){
        if(game ==null){
            throw new GameNotFoundResponseException();
        }
        validateWord(game.getWord());
        validateDate(game.getDate());
        validatePhoneticMiniGame(game.getPhoneticMiniGame());
        validateDefinitionMiniGame(game.getDefinitionMiniGame());
        validateSynonymMiniGame(game.getSynonymMiniGame());
    }

    public void validateSynonymMiniGame(List<String> synonymMiniGame) {
        if(!Validator.objectsValid(synonymMiniGame)){
            throw new ObjectWithoutParamResponseException(Game.class, "sinonyms", String.class);
        }
        for(String word: synonymMiniGame){
            validateWord(word);
        }
        if(!WordValidator.allDifferent(synonymMiniGame)){
            throw new GameRepeatedOnMiniGameResponseException("synonym");
        }
    }

    public void validateDefinitionMiniGame(List<DefinitionSingle> definitionMiniGame) {
        if(!Validator.objectsValid(definitionMiniGame)){
            throw new ObjectWithoutParamResponseException(Game.class, "definitions", DefinitionSingle.class);
        }
        for(DefinitionSingle definitionSingle: definitionMiniGame){
            validateWord(definitionSingle.getWord());
            validateDefinition(definitionSingle.getDefinition());
        }
        if(!WordValidator.allWordsDifferent(definitionMiniGame)){
            throw new GameRepeatedOnMiniGameResponseException("definition");
        }
    }

    public void validatePhoneticMiniGame(List<PhoneticSingle> phoneticMiniGame) {
        if(!Validator.objectsValid(phoneticMiniGame)){
            throw new ObjectWithoutParamResponseException(Game.class, "phonetics", PhoneticSingle.class);
        }
        for(PhoneticSingle phoneticSingle: phoneticMiniGame){
            validateWord(phoneticSingle.getWord());
            validatePhonetic(phoneticSingle.getPhonetic());
        }
        if(!WordValidator.allWordsDifferent(phoneticMiniGame)){
            throw new GameRepeatedOnMiniGameResponseException("phonetic");
        }
    }

    public void validateWord(String word){
        if (!Validator.objectsValid(word)) {
            throw new ObjectWithoutParamResponseException(Game.class, "word", String.class);
        }
    }

    public void validateDate(String date){
        if (date == null) {
            throw new ObjectWithoutParamResponseException(Game.class, "date", String.class);
        }
        if (gameDao.findByDate(date).isPresent()) {
            throw new GameImposibleDateResponseException(date);
        }
    }

    public void validatePhonetic(Phonetic phonetic) {
        if (phonetic == null) {
            throw new ObjectWithoutParamResponseException(Game.class, "phonetic", String.class);
        }
        if (!Validator.objectsValid(phonetic.getAudio(), phonetic.getSyllables())) {
            throw new ObjectWithoutParamResponseException(Definition.class, "audio/syllables", Short.class);
        }
    }

    public void validateDefinition(Definition definition) {
        if (definition == null) {
            throw new ObjectWithoutParamResponseException(Game.class, "definition", String.class);
        }
        if (!Validator.objectsValid(definition.getType(), definition.getText())) {
            throw new ObjectWithoutParamResponseException(Definition.class, "type/text", String.class);
        }
    }

}
