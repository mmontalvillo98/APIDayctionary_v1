package com.tfg.mariomh.v2.myApi.models.dtos;

import com.tfg.mariomh.v2.myApi.models.entities.GameAnswer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AnswerDTO {

    private List<GameAnswer> gameAnswers;

}
