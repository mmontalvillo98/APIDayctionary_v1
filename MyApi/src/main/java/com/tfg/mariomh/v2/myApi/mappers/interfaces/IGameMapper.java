package com.tfg.mariomh.v2.myApi.mappers.interfaces;

import com.tfg.mariomh.v2.myApi.models.dtos.GameDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;

public interface IGameMapper {
    Game gameDtoToGame(GameDTO source);

}
