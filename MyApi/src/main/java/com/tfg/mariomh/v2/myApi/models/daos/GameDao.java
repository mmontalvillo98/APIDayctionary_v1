package com.tfg.mariomh.v2.myApi.models.daos;

import com.tfg.mariomh.v2.myApi.models.entities.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface GameDao extends MongoRepository<Game, String> {

    final static String FIND_BY_DATE = "{date: '?0'}";
    final static String FIND_BY_ID = "{id: '?0'}";
    @Query(FIND_BY_DATE)
    Optional<Game> findByDate(String date);
    @Query(FIND_BY_ID)
    Optional<Game> findById(String id);
    @Query(value=FIND_BY_DATE, fields="{word:1}")
    Optional<Game> findWordByDate(String date);

}
