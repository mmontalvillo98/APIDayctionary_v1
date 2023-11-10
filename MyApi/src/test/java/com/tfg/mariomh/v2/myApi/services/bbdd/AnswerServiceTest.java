package com.tfg.mariomh.v2.myApi.services.bbdd;

import com.tfg.mariomh.v2.myApi.exceptions.responses.AnswerDoneResponseException;
import com.tfg.mariomh.v2.myApi.exceptions.responses.GameNotFoundResponseException;
import com.tfg.mariomh.v2.myApi.exceptions.responses.ObjectWithoutParamResponseException;
import com.tfg.mariomh.v2.myApi.exceptions.responses.UserNotFoundResponseException;
import com.tfg.mariomh.v2.myApi.mappers.interfaces.IAnswerMapper;
import com.tfg.mariomh.v2.myApi.models.daos.AnswerDao;
import com.tfg.mariomh.v2.myApi.models.daos.GameDao;
import com.tfg.mariomh.v2.myApi.models.daos.UserDao;
import com.tfg.mariomh.v2.myApi.models.dtos.AnswerDTO;
import com.tfg.mariomh.v2.myApi.models.dtos.GameAnswerDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Answer;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.models.entities.GameAnswer;
import com.tfg.mariomh.v2.myApi.models.entities.User;
import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.AnswerServiceImpl;
import com.tfg.mariomh.v2.myApi.types.MiniGame;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AnswerServiceTest {
    public final static String USER_ID = "USER_ID";
    public final static String GAME_ID = "GAME_ID";
    public final static MiniGame MINI_GAME = MiniGame.PHONETIC;
    public final static MiniGame OTHER_MINI_GAME = MiniGame.DEFINITION;
    public final static String WORD = "WORD";
    @InjectMocks
    private AnswerServiceImpl answerService;
    @Mock
    private GameDao gameDao;
    @Mock
    private UserDao userDao;
    @Mock
    private IAnswerMapper answerMapper;
    @Mock
    private AnswerDao answerDao;

    @BeforeEach
    public void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void answerGameTestValidCreate(){
        Mockito.doReturn(Optional.of(user())).when(userDao).findById(Mockito.anyString());
        Mockito.doReturn(Optional.of(game())).when(gameDao).findById(Mockito.anyString());
        Mockito.doReturn(answer()).when(answerDao).save(Mockito.any(Answer.class));
        Mockito.doReturn(answerDTO()).when(answerMapper).answerToAnswerDTO(Mockito.any(Answer.class));
        Assertions.assertEquals(answerDTO(), answerService.answerGame(gameAnswerDTO()));
    }
    @Test
    public void answerGameTestNoMiniGame(){
        GameAnswerDTO gameAnswerDTO = gameAnswerDTO();
        gameAnswerDTO.setMiniGame(null);
        Assertions.assertThrows(ObjectWithoutParamResponseException.class, ()->answerService.answerGame(gameAnswerDTO));
    }
    @Test
    public void answerGameTestNoWord(){
        GameAnswerDTO gameAnswerDTO = gameAnswerDTO();
        gameAnswerDTO.setWord(null);
        Assertions.assertThrows(ObjectWithoutParamResponseException.class, ()->answerService.answerGame(gameAnswerDTO));
    }
    @Test
    public void answerGameTestNoUser(){
        Assertions.assertThrows(UserNotFoundResponseException.class, ()->answerService.answerGame(gameAnswerDTO()));
    }
    @Test
    public void answerGameTestNoGame(){
        Mockito.doReturn(Optional.of(user())).when(userDao).findById(Mockito.anyString());
        Assertions.assertThrows(GameNotFoundResponseException.class, ()->answerService.answerGame(gameAnswerDTO()));
    }
    @Test
    public void answerGameTestValidUpdate(){
        Mockito.doReturn(Optional.of(user())).when(userDao).findById(Mockito.anyString());
        Mockito.doReturn(Optional.of(game())).when(gameDao).findById(Mockito.anyString());
        Mockito.doReturn(Optional.of(answer())).when(answerDao).findAnswerByUserGame(Mockito.anyString(), Mockito.anyString());
        Mockito.doReturn(answer()).when(answerDao).save(Mockito.any(Answer.class));
        Mockito.doReturn(answerDTO()).when(answerMapper).answerToAnswerDTO(Mockito.any(Answer.class));
        Assertions.assertEquals(answerDTO(), answerService.answerGame(otherGameAnswerDTO()));
    }
    @Test
    public void answerGameTestRepeatedAnswer(){
        Mockito.doReturn(Optional.of(user())).when(userDao).findById(Mockito.anyString());
        Mockito.doReturn(Optional.of(game())).when(gameDao).findById(Mockito.anyString());
        Mockito.doReturn(Optional.of(answer())).when(answerDao).findAnswerByUserGame(Mockito.anyString(), Mockito.anyString());
        Assertions.assertThrows(AnswerDoneResponseException.class,()-> answerService.answerGame(gameAnswerDTO()));
    }

    private AnswerDTO answerDTO(){
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setGameAnswers(List.of(gameAnswer()));
        return answerDTO;
    }
    private GameAnswerDTO gameAnswerDTO(){
        GameAnswerDTO gameAnswerDTO = new GameAnswerDTO();
        gameAnswerDTO.setUserId(USER_ID);
        gameAnswerDTO.setGameId(GAME_ID);
        gameAnswerDTO.setMiniGame(MINI_GAME);
        gameAnswerDTO.setWord(WORD);
        return gameAnswerDTO;
    }
    private GameAnswerDTO otherGameAnswerDTO(){
        GameAnswerDTO gameAnswerDTO = new GameAnswerDTO();
        gameAnswerDTO.setUserId(USER_ID);
        gameAnswerDTO.setGameId(GAME_ID);
        gameAnswerDTO.setMiniGame(OTHER_MINI_GAME);
        gameAnswerDTO.setWord(WORD);
        return gameAnswerDTO;
    }
    private GameAnswer gameAnswer(){
        return new GameAnswer(MINI_GAME, WORD);
    }
    private GameAnswer otherGameAnswer(){
        return new GameAnswer(OTHER_MINI_GAME, WORD);
    }
    private Answer answer(){
        Answer answer = new Answer();
        answer.setUser(user());
        answer.setGame(game());
        answer.setGameAnswers(new ArrayList<>());
        answer.getGameAnswers().add(gameAnswer());
        return answer;
    }
    private User user(){
        User user = new User();
        user.setId(USER_ID);
        user.setAnswers(new ArrayList<>());
        return user;
    }
    private Game game(){
        Game game = new Game();
        game.setId(GAME_ID);
        game.setAnswers(new ArrayList<>());
        return game;
    }

}
