package com.tfg.mariomh.v2.myApi.controllers;

import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.GameServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WordControllerTest {
    private final static String WORD = "WORD";
    private final static String ID = "ID";
    private final static String DATE = "19980701";
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    GameServiceImpl gameService;

    @Test
    public void getTodayWordTest(){
        ParameterizedTypeReference<Game> typeRef =new ParameterizedTypeReference<Game>() {};
        Mockito.doReturn(game()).when(gameService).getDateWord(Mockito.anyString());
        Game response = webTestClient.get()
                .uri("/words")
                .accept(MediaType.APPLICATION_JSON)
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
    public void getDateWordTest(){
        ParameterizedTypeReference<Game> typeRef =new ParameterizedTypeReference<Game>() {};
        Mockito.doReturn(game()).when(gameService).getDateWord(Mockito.anyString());
        Game response = webTestClient.get()
                .uri("/words/{date}", DATE)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response.getId());
    }

    private Game game(){
        Game game = new Game();
        game.setId(ID);
        game.setWord(WORD);
        game.setDate(DATE);
        return game;
    }

}
