package com.tfg.mariomh.v2.myApi.controllers;

import com.tfg.mariomh.v2.myApi.models.entities.SynonymSingle;
import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.SynonymServiceImpl;
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

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SynonymControllerTest {

    private final static String WORD = "WORD";
    private final static String ID = "ID";
    private final static String DATE = "19980701";
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    SynonymServiceImpl synonymService;

    @Test
    public void getTodayMiniGame(){
        String token = TokenUtils.createToken(ID, List.of(Role.USER));
        ParameterizedTypeReference<List<SynonymSingle>> typeRef =new ParameterizedTypeReference<List<SynonymSingle>>() {};
        Mockito.doReturn(synonymSingleList()).when(synonymService).getList(Mockito.anyString());
        List<SynonymSingle> response = webTestClient.get()
                .uri("/mini/synonyms")
                .accept(MediaType.APPLICATION_JSON)
                .headers(http -> http.setBearerAuth(token))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(WORD, response.get(0).getWord());
    }
    @Test
    public void getMiniGameByDate(){
        String token = TokenUtils.createToken(ID, List.of(Role.USER));
        ParameterizedTypeReference<List<SynonymSingle>> typeRef =new ParameterizedTypeReference<List<SynonymSingle>>() {};
        Mockito.doReturn(synonymSingleList()).when(synonymService).getList(Mockito.anyString());
        List<SynonymSingle> response = webTestClient.get()
                .uri("/mini/synonyms/{date}", DATE)
                .accept(MediaType.APPLICATION_JSON)
                .headers(http -> http.setBearerAuth(token))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(WORD, response.get(0).getWord());
    }
    private List<SynonymSingle> synonymSingleList(){
        SynonymSingle synonymSingle = new SynonymSingle();
        synonymSingle.setWord(WORD);
        return List.of(synonymSingle);
    }

}
