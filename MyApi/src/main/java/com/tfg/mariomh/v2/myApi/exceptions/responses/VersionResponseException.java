package com.tfg.mariomh.v2.myApi.exceptions.responses;

import com.tfg.mariomh.v2.myApi.exceptions.responses.MyApiResponseException;
import org.springframework.http.HttpStatus;

public class VersionResponseException extends MyApiResponseException {
    public VersionResponseException() {
        super(HttpStatus.CONFLICT, "Version doesn't match");
    }
}
