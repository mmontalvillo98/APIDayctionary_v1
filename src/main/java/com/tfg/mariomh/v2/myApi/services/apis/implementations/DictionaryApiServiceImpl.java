package com.tfg.mariomh.v2.myApi.services.apis.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.requests.RequestApiError;
import com.tfg.mariomh.v2.myApi.models.dtos.DictionaryApiResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class DictionaryApiServiceImpl extends ExternalApiService {
    private final static String URL = "https://api.dictionaryapi.dev/api/v2/entries/en/"+URL_PLACEHOLDER;
    public DictionaryApiResponseDTO get(String word) throws RequestApiError {
        return new DictionaryApiResponseDTO(requestGet(replacePlaceholder(URL, word)));
    }
}
