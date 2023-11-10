package com.tfg.mariomh.v2.myApi.services.bbdd.implementations;

import com.tfg.mariomh.v2.myApi.mappers.implementations.MiniGameMapperImpl;
import com.tfg.mariomh.v2.myApi.models.daos.GameDao;
import com.tfg.mariomh.v2.myApi.models.entities.DefinitionSingle;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefinitionServiceImpl extends MiniGameService<DefinitionSingle> {
    public DefinitionServiceImpl(GameDao gameDao, MiniGameMapperImpl mapper) {
        super(gameDao, mapper);
    }

    @Override
    protected List<DefinitionSingle> getAll(Game game) {
        return mapper.gameToDefinitionMiniGame(game);
    }

}
