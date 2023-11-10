package com.tfg.mariomh.v2.myApi.exceptions.responses;

import com.tfg.mariomh.v2.myApi.exceptions.responses.MyApiResponseException;
import org.springframework.http.HttpStatus;

public class UserUsedMailResponseException extends MyApiResponseException {
    public UserUsedMailResponseException(String mail) {
        super(HttpStatus.BAD_REQUEST, String.format("Already used mail: '%s'", mail));
    }
}
