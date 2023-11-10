package com.tfg.mariomh.v2.myApi.exceptions.responses;

import com.tfg.mariomh.v2.myApi.exceptions.responses.MyApiResponseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundResponseException extends MyApiResponseException {
    public UserNotFoundResponseException() {
        super(HttpStatus.NOT_FOUND, "User not found");
    }
}
