package com.tfg.mariomh.v2.myApi.mappers;

import com.tfg.mariomh.v2.myApi.mappers.implementations.AnswerMapperImpl;
import com.tfg.mariomh.v2.myApi.models.dtos.AnswerDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Answer;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.models.entities.GameAnswer;
import com.tfg.mariomh.v2.myApi.models.entities.User;
import com.tfg.mariomh.v2.myApi.types.MiniGame;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AnswerMapperImplTest {
    private final static String ID = "1D";
    @InjectMocks
    private AnswerMapperImpl answerMapper;

    @BeforeEach
    public void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void answerToAnswerDTO(){
        AnswerDTO resultAnswerDTO = answerMapper.answerToAnswerDTO(answer());
        Assertions.assertEquals(answerDTO().getGameAnswers().size(), resultAnswerDTO.getGameAnswers().size());
        for (int i=0; i<answer().getGameAnswers().size(); i++) {
            Assertions.assertEquals(answerDTO().getGameAnswers().get(i), resultAnswerDTO.getGameAnswers().get(i));
        }
    }
    private AnswerDTO answerDTO(){
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setGameAnswers(List.of(gameAnswer()));
        return answerDTO;
    }
    private Answer answer(){
        return new Answer(user(), game(), List.of(gameAnswer()));
    }
    private User user(){
        User user = new User();
        user.setId(ID);
        return user;
    }
    private Game game(){
        Game game = new Game();
        game.setId(ID);
        return game;
    }
    private GameAnswer gameAnswer(){
        return new GameAnswer(MiniGame.PHONETIC, "Word");
    }

}
