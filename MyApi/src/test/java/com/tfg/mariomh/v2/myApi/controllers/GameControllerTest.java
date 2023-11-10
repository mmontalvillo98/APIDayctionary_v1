package com.tfg.mariomh.v2.myApi.controllers;

import com.tfg.mariomh.v2.myApi.models.dtos.GameDTO;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.GameServiceImpl;
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

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {

    private final static String WORD = "WORD";
    private final static String ID = "ID";
    private final static String DATE = "19980701";
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    GameServiceImpl gameService;

    @Test
    public void saveTest(){
        String token = TokenUtils.createToken(ID, List.of(Role.ADMIN));
        ParameterizedTypeReference<Game> typeRef =new ParameterizedTypeReference<Game>() {};
        Mockito.doReturn(game()).when(gameService).save(Mockito.any(GameDTO.class));
        Game response = webTestClient.post()
                .uri("/games")
                .body(BodyInserters.fromPublisher(Mono.just(gameDTO()), GameDTO.class))
                .accept(MediaType.APPLICATION_JSON)
                .headers(http -> http.setBearerAuth(token))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response.getId());
    }
    @Test
    public void getGameByDate(){
        String token = TokenUtils.createToken(ID, List.of(Role.ADMIN));
        ParameterizedTypeReference<Game> typeRef =new ParameterizedTypeReference<Game>() {};
        Mockito.doReturn(game()).when(gameService).getByDate(Mockito.anyString());
        Game response = webTestClient.get()
                .uri("/games/{date}", DATE)
                .accept(MediaType.APPLICATION_JSON)
                .headers(http -> http.setBearerAuth(token))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response.getId());
    }
    @Test
    public void createRandomGame(){
        String token = TokenUtils.createToken(ID, List.of(Role.ADMIN));
        ParameterizedTypeReference<Game> typeRef =new ParameterizedTypeReference<Game>() {};
        Mockito.doReturn(game()).when(gameService).createRandomGame();
        Game response = webTestClient.get()
                .uri("/games")
                .accept(MediaType.APPLICATION_JSON)
                .headers(http -> http.setBearerAuth(token))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response.getId());
    }
    @Test
    public void createGameFromWord(){
        String token = TokenUtils.createToken(ID, List.of(Role.ADMIN));
        ParameterizedTypeReference<Game> typeRef =new ParameterizedTypeReference<Game>() {};
        Mockito.doReturn(game()).when(gameService).createGameFromWord(Mockito.anyString());
        Game response = webTestClient.get()
                .uri("/games/word/{word}", WORD)
                .accept(MediaType.APPLICATION_JSON)
                .headers(http -> http.setBearerAuth(token))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response.getId());
    }

    private GameDTO gameDTO(){
        GameDTO gameDTO = new GameDTO();
        gameDTO.setDate(DATE);
        return gameDTO;
    }
    private Game game(){
        Game game = new Game();
        game.setId(ID);
        game.setWord(WORD);
        game.setDate(DATE);
        return game;
    }

}
