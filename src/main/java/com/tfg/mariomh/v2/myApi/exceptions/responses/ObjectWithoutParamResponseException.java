package com.tfg.mariomh.v2.myApi.exceptions.responses;

import com.tfg.mariomh.v2.myApi.exceptions.responses.MyApiResponseException;
import org.springframework.http.HttpStatus;

public class ObjectWithoutParamResponseException extends MyApiResponseException {
    public ObjectWithoutParamResponseException(Class clasz, String param, Class paramClasz) {
        super(HttpStatus.BAD_REQUEST, String.format("The class '%s' needs a '%s' ('%s') to be created", clasz.toString(), param, paramClasz.toString()));
    }
}
