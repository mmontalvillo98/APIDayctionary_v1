package com.tfg.mariomh.v2.myApi.exceptions.responses;

import com.tfg.mariomh.v2.myApi.exceptions.responses.MyApiResponseException;
import org.springframework.http.HttpStatus;

public class GameNotFoundResponseException extends MyApiResponseException {
    public GameNotFoundResponseException() {
        super(HttpStatus.BAD_REQUEST, "Game not found");
    }
}
