package com.tfg.mariomh.v2.myApi.services.bbdd.interfaces;

import com.tfg.mariomh.v2.myApi.models.dtos.AnswerDTO;
import com.tfg.mariomh.v2.myApi.models.dtos.GameAnswerDTO;

public interface IAnswerService {

    AnswerDTO answerGame(GameAnswerDTO gameAnswerDTO);
    AnswerDTO getAnswerByUserGame(String userId, String gameId);

}
