package com.tfg.mariomh.v2.myApi.exceptions.responses;

import org.springframework.http.HttpStatus;

public class GameCreationResponseException extends MyApiResponseException{
    public GameCreationResponseException() {
        super(HttpStatus.NOT_FOUND, "The game could not be created, there is no enough information.");
    }
}
