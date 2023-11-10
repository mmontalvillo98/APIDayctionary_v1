package com.tfg.mariomh.v2.myApi.exceptions.responses;

import org.springframework.http.HttpStatus;

public class RequestApiErrorResponseException extends MyApiResponseException{
    public RequestApiErrorResponseException(Integer status, Class apiService, String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, String.format("%s: '%s' (%s) ", apiService.toString(), reason));
    }
}
