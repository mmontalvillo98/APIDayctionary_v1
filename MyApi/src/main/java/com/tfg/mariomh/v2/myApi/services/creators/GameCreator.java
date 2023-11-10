package com.tfg.mariomh.v2.myApi.services.creators;

import com.tfg.mariomh.v2.myApi.exceptions.creation.ObjectCreationError;
import com.tfg.mariomh.v2.myApi.exceptions.creation.GameCreationError;
import com.tfg.mariomh.v2.myApi.exceptions.requests.RequestApiError;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiResponseDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.services.apis.implementations.DictionaryApiServiceImpl;
import com.tfg.mariomh.v2.myApi.services.apis.implementations.RandomRequesterImpl;
import com.tfg.mariomh.v2.myApi.services.creators.implementations.*;
import com.tfg.mariomh.v2.myApi.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
@AllArgsConstructor
public class GameCreator {

    private final PhoneticSimpleCreator phoneticCreator;
    private final DefinitionSimpleCreator definitionCreator;
    private final PhoneticSingleCreator phoneticSingleCreator;
    private final DefinitionSingleCreator definitionSingleCreator;
    private final SynonymCreator synonymCreator;
    private final RandomRequesterImpl randomRequester;
    private final DictionaryApiServiceImpl dictionaryApiService;

    /**
     * Crea un juego de una palabra aleatoria.
     * @return Game juego creado
     */
    public Game create(){
        Game createdWord = null;
        while(createdWord == null){
            try {
                createdWord = createFromDictionary(randomRequester.getDictionaryRegister());
            }catch(ObjectCreationError error){
                log.info(error.getMessage());
            }
        }
        return createdWord;
    }

    /**
     * Intenta crear un juego sobre la palabra indicada
     * @param word palabra
     * @return Game juego creado
     * @throws GameCreationError
     */
    public Game createFromWord(String word) throws GameCreationError {
        try{
            return createFromDictionary(dictionaryApiService.get(word));
        }catch(ObjectCreationError | RequestApiError error){
            log.info(error.getMessage());
            throw new GameCreationError(word, error.getMessage());
        }
    }

    /**
     * Intenta crear un juego a partir de una entrada de diccionario
     * @param response entrada de diccionario de una palabra
     * @return Game juego creado
     * @throws ObjectCreationError
     */
    private Game createFromDictionary(DictionaryApiResponseDTO response) throws ObjectCreationError {
        Game game = new Game();
        game.setWord(response.getEntries().get(0).getWord());
        game.setSynonym(synonymCreator.findSynonym(response));
        game.setPhonetic(phoneticCreator.find(response));
        game.setDefinition(definitionCreator.find(response));
        game.setSimilarSynonyms(synonymCreator.findSimilars(game, response));
        game.setSimilarPhonetics(phoneticSingleCreator.findSimilars(response, game));
        game.setSimilarDefinitions(definitionSingleCreator.findSimilars(response, game));
        return game;
    }

}
