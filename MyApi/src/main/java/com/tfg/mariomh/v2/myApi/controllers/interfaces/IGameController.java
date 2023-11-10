package com.tfg.mariomh.v2.myApi.controllers.interfaces;

import com.tfg.mariomh.v2.myApi.models.dtos.GameDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/games")
public interface IGameController {

    @PostMapping("")
    ResponseEntity<Game> save(@RequestBody GameDTO gameDTO);
    @GetMapping("/{date}")
    ResponseEntity<Game> getGameByDate(@PathVariable String date);
    @GetMapping("")
    ResponseEntity<Game> createRandomGame();
    @GetMapping("/word/{word}")
    ResponseEntity<Game> createGameFromWord(@PathVariable String word);

}
