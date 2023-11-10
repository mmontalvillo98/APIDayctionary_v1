package com.tfg.mariomh.v2.myApi.mappers.interfaces;

import com.tfg.mariomh.v2.myApi.models.dtos.AnswerDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Answer;

public interface IAnswerMapper {

    AnswerDTO answerToAnswerDTO(Answer source);

}
