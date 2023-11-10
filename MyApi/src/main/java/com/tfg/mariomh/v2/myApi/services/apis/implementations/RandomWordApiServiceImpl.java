package com.tfg.mariomh.v2.myApi.services.apis.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.requests.RequestApiError;
import com.tfg.mariomh.v2.myApi.models.dtos.RandomWordApiResponseDTO;
import com.tfg.mariomh.v2.myApi.services.apis.QueryParam;
import com.tfg.mariomh.v2.myApi.services.apis.QueryParams;
import org.springframework.stereotype.Service;

@Service
public class RandomWordApiServiceImpl extends ExternalApiService {
    private final static String URL = "https://random-word-api.herokuapp.com/word";
    private final static String QUERY_NUMBER = "number";
    private final static String LANGUAGE = "lang";
    private final static String ENGLISH = "en";
    private final static String LENGTH = "length";
    public final static Short RANDOM_WORDS_REQUESTED = 1000;

    public RandomWordApiResponseDTO getOne() throws RequestApiError {
        return new RandomWordApiResponseDTO(requestGet(URL));
    }
    public RandomWordApiResponseDTO getSome(Short number) throws RequestApiError {
        return new RandomWordApiResponseDTO(requestGetWithParams(URL, getSimpleParams(number)));
    }

    public RandomWordApiResponseDTO getSomeWithLetters(Short number, Integer letters) throws RequestApiError {
        return new RandomWordApiResponseDTO(requestGetWithParams(URL, getComplexParams(number, letters)));
    }

    private QueryParams getSimpleParams(Short number){
        return new QueryParams(
                new QueryParam(QUERY_NUMBER, String.format("%s", number)),
                new QueryParam(LANGUAGE, ENGLISH)
        );
    }

    public QueryParams getComplexParams(Short number, Integer letters){
        QueryParams queryParams = getSimpleParams(number);
        queryParams.add(new QueryParam(LENGTH, String.format("%s", letters)));
        return  queryParams;
    }


}
