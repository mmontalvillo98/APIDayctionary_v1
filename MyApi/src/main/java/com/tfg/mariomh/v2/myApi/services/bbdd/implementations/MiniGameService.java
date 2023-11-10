package com.tfg.mariomh.v2.myApi.services.bbdd.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.responses.GameImposibleDateResponseException;
import com.tfg.mariomh.v2.myApi.exceptions.responses.GameNotFoundResponseException;
import com.tfg.mariomh.v2.myApi.mappers.implementations.MiniGameMapperImpl;
import com.tfg.mariomh.v2.myApi.models.daos.GameDao;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import lombok.AllArgsConstructor;

import java.time.DateTimeException;
import java.util.List;

@AllArgsConstructor
public abstract class MiniGameService<T> {

    protected final GameDao gameDao;
    protected final MiniGameMapperImpl mapper;

    /**
     * Extrae los elementos que forman el minijuego del juego
     * @param game juego completo
     * @return List lista de elementos del minijuego
     */
    protected abstract List<T> getAll(Game game);

    /**
     * Obtiene el juego completo de una fecha concreta
     * @param date fecha con formato YYYYMMDD
     * @return Game juego del día indicado
     */
    private Game getGame(String date) {
        return gameDao.findByDate(date).orElseThrow(()->new GameNotFoundResponseException());
    }

    /**
     * Obtiene la lista de elementos que forman un minijuego
     * @param date fecha con formato YYYYMMDD
     * @return List lista de elementos
     */
    public List<T> getList(String date) {
        try{
            return getAll(getGame(date));
        }catch(DateTimeException e){
            throw new GameImposibleDateResponseException(date);
        }
    }
}
