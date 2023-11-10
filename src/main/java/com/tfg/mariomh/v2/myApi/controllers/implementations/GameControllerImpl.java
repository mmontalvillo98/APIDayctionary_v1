package com.tfg.mariomh.v2.myApi.controllers.implementations;

import com.tfg.mariomh.v2.myApi.controllers.interfaces.IGameController;
import com.tfg.mariomh.v2.myApi.models.dtos.GameDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.services.bbdd.interfaces.IGameService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GameControllerImpl implements IGameController {

    private final IGameService gameService;


    @Override
    public ResponseEntity<Game> save(GameDTO gameDTO) {
        return new ResponseEntity<>(gameService.save(gameDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Game> getGameByDate(String date) {
        return new ResponseEntity<>(gameService.getByDate(date), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Game> createRandomGame() {
        return new ResponseEntity<>(gameService.createRandomGame(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Game> createGameFromWord(String word) {
        return new ResponseEntity<>(gameService.createGameFromWord(word), HttpStatus.CREATED);
    }

}
