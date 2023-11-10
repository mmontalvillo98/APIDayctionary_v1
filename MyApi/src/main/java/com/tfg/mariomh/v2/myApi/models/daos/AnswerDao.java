package com.tfg.mariomh.v2.myApi.models.daos;

import com.tfg.mariomh.v2.myApi.models.entities.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface AnswerDao extends MongoRepository<Answer, String> {

    @Query("{user:ObjectId('?0'), game:ObjectId('?1')}")
    Optional<Answer> findAnswerByUserGame(String userId, String gameId);

}
