package com.tfg.mariomh.v2.myApi.mappers.implementations;

import com.tfg.mariomh.v2.myApi.mappers.interfaces.IAnswerMapper;
import com.tfg.mariomh.v2.myApi.models.dtos.AnswerDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Answer;
import org.springframework.stereotype.Service;

@Service
public class AnswerMapperImpl implements IAnswerMapper {

    @Override
    public AnswerDTO answerToAnswerDTO(Answer source) {
        AnswerDTO target = null;
        if (source!=null) {
            target = new AnswerDTO();
            target.setGameAnswers(source.getGameAnswers());
        }
        return target;
    }
}
