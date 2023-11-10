package com.tfg.mariomh.v2.myApi.controllers.implementations;

import com.tfg.mariomh.v2.myApi.controllers.interfaces.IWordController;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.services.bbdd.interfaces.IGameService;
import com.tfg.mariomh.v2.myApi.utils.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WordControllerImpl implements IWordController {

    private final IGameService gameService;

    @Override
    public ResponseEntity<Game> getTodayWord() {
        return new ResponseEntity<>(gameService.getDateWord(DateUtil.getTodayDate()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Game> getDateWord(String date) {
        return new ResponseEntity<>(gameService.getDateWord(date), HttpStatus.OK);
    }

}
