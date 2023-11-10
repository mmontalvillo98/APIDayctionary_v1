package com.tfg.mariomh.v2.myApi.exceptions.requests;

public class RequestApiError extends Exception{

    public RequestApiError(Integer status, Class apiService, String reason) {
        super(String.format("%s: '%s' (%s) ", status.toString(), apiService.toString(), reason));
    }
}
