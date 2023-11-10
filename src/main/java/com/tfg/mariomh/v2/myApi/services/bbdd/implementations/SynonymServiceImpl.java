package com.tfg.mariomh.v2.myApi.services.bbdd.implementations;

import com.tfg.mariomh.v2.myApi.mappers.implementations.MiniGameMapperImpl;
import com.tfg.mariomh.v2.myApi.models.daos.GameDao;
import com.tfg.mariomh.v2.myApi.models.entities.SynonymSingle;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SynonymServiceImpl extends MiniGameService<SynonymSingle> {


    public SynonymServiceImpl(GameDao gameDao, MiniGameMapperImpl mapper) {
        super(gameDao, mapper);
    }

    @Override
    protected List<SynonymSingle> getAll(Game game) {
        return mapper.gameToSynonymMiniGame(game);
    }

}
