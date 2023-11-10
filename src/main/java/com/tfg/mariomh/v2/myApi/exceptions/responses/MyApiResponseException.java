package com.tfg.mariomh.v2.myApi.exceptions.responses;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class MyApiResponseException extends ResponseStatusException {

    public MyApiResponseException(HttpStatus status, String reason) {
        super(status, reason);
    }


}
