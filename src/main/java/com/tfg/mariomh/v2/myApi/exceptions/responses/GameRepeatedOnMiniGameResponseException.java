package com.tfg.mariomh.v2.myApi.exceptions.responses;

import com.tfg.mariomh.v2.myApi.exceptions.responses.MyApiResponseException;
import org.springframework.http.HttpStatus;

public class GameRepeatedOnMiniGameResponseException extends MyApiResponseException {
    public GameRepeatedOnMiniGameResponseException(String miniGame) {
        super(HttpStatus.BAD_REQUEST, String.format("Repeated element on miniGame: '%s'", miniGame));
    }
}
