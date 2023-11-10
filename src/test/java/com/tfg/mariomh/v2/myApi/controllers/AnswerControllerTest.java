package com.tfg.mariomh.v2.myApi.controllers;

import com.tfg.mariomh.v2.myApi.models.dtos.AnswerDTO;
import com.tfg.mariomh.v2.myApi.models.dtos.GameAnswerDTO;
import com.tfg.mariomh.v2.myApi.models.entities.GameAnswer;
import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.AnswerServiceImpl;
import com.tfg.mariomh.v2.myApi.types.MiniGame;
import com.tfg.mariomh.v2.myApi.types.Role;
import com.tfg.mariomh.v2.myApi.utils.TokenUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnswerControllerTest {

    public final static String GAME_ID = "GAME_ID";
    public final static String USER_ID = "USER_ID";
    public final static String WORD = "WORD";
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    AnswerServiceImpl answerService;
    @Test
    public void answerGameTest(){
        String token = TokenUtils.createToken(USER_ID, List.of(Role.USER));
        ParameterizedTypeReference<AnswerDTO> typeRef =new ParameterizedTypeReference<AnswerDTO>() {};
        Mockito.doReturn(answerDTO()).when(answerService).answerGame(Mockito.any(GameAnswerDTO.class));
        AnswerDTO responseBody = webTestClient.post()
                .uri("/answers/answer")
                .body(BodyInserters.fromPublisher(Mono.just(gameAnswerDTO()), GameAnswerDTO.class))
                .headers(http -> http.setBearerAuth(token))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(1, responseBody.getGameAnswers().size());
        Assertions.assertEquals(WORD, responseBody.getGameAnswers().get(0).getWord());
    }
    @Test
    public void getAnswerByUserGameTest(){
        String token = TokenUtils.createToken(USER_ID, List.of(Role.USER));
        ParameterizedTypeReference<AnswerDTO> typeRef =new ParameterizedTypeReference<AnswerDTO>() {};
        Mockito.doReturn(answerDTO()).when(answerService).getAnswerByUserGame(Mockito.anyString(), Mockito.anyString());
        AnswerDTO responseBody = webTestClient.get()
                .uri("/answers/answer/{userId}/{gameId}", USER_ID, GAME_ID)
                .headers(http -> http.setBearerAuth(token))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(1, responseBody.getGameAnswers().size());
        Assertions.assertEquals(WORD, responseBody.getGameAnswers().get(0).getWord());
    }
    private AnswerDTO answerDTO(){
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setGameAnswers(new ArrayList<>());
        answerDTO.getGameAnswers().add(gameAnswer());
        return answerDTO;
    }
    private GameAnswer gameAnswer(){
        GameAnswer gameAnswer = new GameAnswer();
        gameAnswer.setMiniGame(MiniGame.PHONETIC);
        gameAnswer.setWord(WORD);
        return gameAnswer;
    }
    private GameAnswerDTO gameAnswerDTO(){
        GameAnswerDTO gameAnswerDTO = new GameAnswerDTO();
        gameAnswerDTO.setGameId(GAME_ID);
        gameAnswerDTO.setUserId(USER_ID);
        gameAnswerDTO.setMiniGame(MiniGame.PHONETIC);
        gameAnswerDTO.setWord(WORD);
        return gameAnswerDTO;
    }
}
