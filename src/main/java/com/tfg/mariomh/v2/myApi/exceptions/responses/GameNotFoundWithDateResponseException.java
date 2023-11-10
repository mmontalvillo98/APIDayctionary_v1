package com.tfg.mariomh.v2.myApi.exceptions.responses;

import com.tfg.mariomh.v2.myApi.utils.DateUtil;
import org.springframework.http.HttpStatus;

public class GameNotFoundWithDateResponseException extends MyApiResponseException {
    public GameNotFoundWithDateResponseException(String date) {
        super(HttpStatus.NOT_FOUND, String.format("Game not found with date: '%s'", DateUtil.showDate(date)));
    }
}
