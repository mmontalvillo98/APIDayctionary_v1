package com.tfg.mariomh.v2.myApi.exceptions.responses;

import com.tfg.mariomh.v2.myApi.exceptions.responses.MyApiResponseException;
import org.springframework.http.HttpStatus;

public class AnswerNotFoundResponseException extends MyApiResponseException {
    public AnswerNotFoundResponseException() {
        super(HttpStatus.NOT_FOUND, "Answer not found");
    }
}
