package com.tfg.mariomh.v2.myApi.exceptions.responses;

import com.tfg.mariomh.v2.myApi.exceptions.responses.MyApiResponseException;
import org.springframework.http.HttpStatus;

public class GameImposibleDateResponseException extends MyApiResponseException {
    public GameImposibleDateResponseException(String date) {
        super(HttpStatus.BAD_REQUEST, String.format("'%s' is not a valid date", date));
    }
}
