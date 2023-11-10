package com.tfg.mariomh.v2.myApi.controllers.interfaces;

import com.tfg.mariomh.v2.myApi.models.entities.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/words")
public interface IWordController {
    @GetMapping()
    ResponseEntity<Game> getTodayWord();
    @GetMapping("/{date}")
    ResponseEntity<Game> getDateWord(@PathVariable String date);
}
