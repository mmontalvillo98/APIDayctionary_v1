package com.tfg.mariomh.v2.myApi.models.dtos;

import com.tfg.mariomh.v2.myApi.types.MiniGame;
import lombok.Data;

@Data
public class GameAnswerDTO {

    String userId;
    String gameId;
    MiniGame miniGame;
    String word;

}
