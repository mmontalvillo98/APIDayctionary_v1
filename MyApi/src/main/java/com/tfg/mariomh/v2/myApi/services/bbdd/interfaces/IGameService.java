package com.tfg.mariomh.v2.myApi.services.bbdd.interfaces;

import com.tfg.mariomh.v2.myApi.models.dtos.GameDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;

public interface IGameService {
    Game save(GameDTO gameDTO);
    Game getByDate(String date);
    Game getDateWord(String date);
    Game createRandomGame();
    Game createGameFromWord(String word);
}
