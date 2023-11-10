package com.tfg.mariomh.v2.myApi.services.apis.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.requests.RequestApiError;
import com.tfg.mariomh.v2.myApi.models.dtos.DatamuseApiResponseDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.services.apis.QueryParam;
import com.tfg.mariomh.v2.myApi.services.apis.QueryParams;
import org.springframework.stereotype.Service;

@Service
public class DatamuseApiServiceImpl extends ExternalApiService {

    private final static String URL = "https://api.datamuse.com/words";
    private final static String ADJETIVES_USED_TO_DESCRIBE = "rel_jjb";
    private final static String ASOCIATED_WITH = "rel_trg";
    private final static String MEANING_LIKE = "ml";
    private final static String NUMBER = "max";
    private final static String OFTEN_FOLLOWERS_OF = "lc";
    private final static String RIMES_WITH = "rel_rhy";
    private final static String SOUND_LIKE = "sl";
    private final static String SPELLED_LIKE = "sl";

    public DatamuseApiResponseDTO getMeaningLike(Game game) throws RequestApiError {
        return new DatamuseApiResponseDTO(requestGetWithParams(URL, getSimpleParams(MEANING_LIKE, game)));
    }

    public DatamuseApiResponseDTO getSoundLike(Game game) throws RequestApiError {
        return new DatamuseApiResponseDTO(requestGetWithParams(URL, getParamsSoundLike(SOUND_LIKE, game)));
    }

    public DatamuseApiResponseDTO getSoundLike(String word) throws RequestApiError {
        Game game = new Game();
        game.setWord(word);
        return getSoundLike(game);
    }

    public DatamuseApiResponseDTO getAsociatedWith(Game game) throws RequestApiError {
        return new DatamuseApiResponseDTO(requestGetWithParams(URL, getSimpleParams(ASOCIATED_WITH, game)));
    }

    public DatamuseApiResponseDTO getAdjectivesUsedToDescribe(Game game) throws RequestApiError {
        return new DatamuseApiResponseDTO(requestGetWithParams(URL, getSimpleParams(ADJETIVES_USED_TO_DESCRIBE, game)));
    }

    public DatamuseApiResponseDTO getRimesWith(Game game) throws RequestApiError {
        return new DatamuseApiResponseDTO(requestGetWithParams(URL, getSimpleParams(RIMES_WITH, game)));
    }

    public DatamuseApiResponseDTO getOftenFollowersOf(Game game) throws RequestApiError {
        return new DatamuseApiResponseDTO(requestGetWithParams(URL, getSimpleParams(OFTEN_FOLLOWERS_OF, game)));
    }

    private QueryParams getSimpleParams(String typeOfSearch, Game game) {
        return new QueryParams(
                new QueryParam(typeOfSearch, game.getWord()),
                new QueryParam(NUMBER, "100")
        );
    }

    private QueryParams getParamsSoundLike(String typeOfSearch, Game game){
        QueryParams params = getSimpleParams(typeOfSearch, game);
        //params.add(new QueryParam(SPELLED_LIKE, wordGame.getWordPattern()));
        return params;
    }

}
