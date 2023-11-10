package com.tfg.mariomh.v2.myApi.exceptions.responses;

import com.tfg.mariomh.v2.myApi.exceptions.responses.MyApiResponseException;
import org.springframework.http.HttpStatus;

public class MailNotSendException extends MyApiResponseException {
    public MailNotSendException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "The mail could not be sent");
    }
}
