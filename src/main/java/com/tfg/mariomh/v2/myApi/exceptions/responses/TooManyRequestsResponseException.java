package com.tfg.mariomh.v2.myApi.exceptions.responses;

import org.springframework.http.HttpStatus;

public class TooManyRequestsResponseException extends MyApiResponseException{
    public TooManyRequestsResponseException(String api) {
        super(HttpStatus.TOO_MANY_REQUESTS, String.format("To many requests done to '%s'", api));
    }
}
