package com.tfg.mariomh.v2.myApi.controllers;

import com.tfg.mariomh.v2.myApi.models.dtos.UserDTO;
import com.tfg.mariomh.v2.myApi.models.entities.User;
import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.UserServiceImpl;
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
public class UserControllerTest {

    private final static String ID = "ID";
    private final static String MAIL = "MAIL";
    private final static String PASS = "PASS";
    private final static Boolean NOTIFY = Boolean.TRUE;
    private final static Long VERSION = 0L;
    private final static Role ROLE = Role.USER;
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    UserServiceImpl userService;

    @Test
    public void getByidTest(){
        String token = TokenUtils.createToken(ID, List.of(Role.USER));
        ParameterizedTypeReference<UserDTO> typeRef =new ParameterizedTypeReference<UserDTO>() {};
        Mockito.doReturn(userDTO()).when(userService).getByIdInfoUser(Mockito.anyString());
        UserDTO responseBody = webTestClient.get()
                .uri("/users/token/{id}", ID)
                .headers(http -> http.setBearerAuth(token))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(ID, responseBody.getId());
    }
    @Test
    public void stopNotificationsTest(){
        ParameterizedTypeReference<Boolean> typeRef =new ParameterizedTypeReference<Boolean>() {};
        Mockito.doReturn(Boolean.TRUE).when(userService).stopNotifications(Mockito.anyString());
        Boolean responseBody = webTestClient.get()
                .uri("/users/stopNotifications/{mail}", MAIL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(Boolean.TRUE, responseBody);
    }
    @Test
    public void usedMailTest(){
        ParameterizedTypeReference<Boolean> typeRef =new ParameterizedTypeReference<Boolean>() {};
        Mockito.doReturn(Boolean.TRUE).when(userService).usedMail(Mockito.anyString());
        Boolean responseBody = webTestClient.get()
                .uri("/users/{mail}", MAIL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(Boolean.TRUE, responseBody);
    }
    @Test
    public void deleteByIdTest(){
        ParameterizedTypeReference<Boolean> typeRef =new ParameterizedTypeReference<Boolean>() {};
        Mockito.doReturn(Boolean.TRUE).when(userService).deleteById(Mockito.anyString(), Mockito.anyLong());
        Boolean responseBody = webTestClient.delete()
                .uri("/users/{userId}/{version}", ID, VERSION)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(Boolean.TRUE, responseBody);
    }
    @Test
    public void saveTest(){
        ParameterizedTypeReference<UserDTO> typeRef =new ParameterizedTypeReference<UserDTO>() {};
        Mockito.doReturn(userDTO()).when(userService).save(Mockito.any(UserDTO.class));
        UserDTO responseBody = webTestClient.post()
                .uri("/users")
                .body(BodyInserters.fromPublisher(Mono.just(userDTO()), UserDTO.class))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(ID, responseBody.getId());
    }
    @Test
    public void updateTest(){
        ParameterizedTypeReference<UserDTO> typeRef =new ParameterizedTypeReference<UserDTO>() {};
        Mockito.doReturn(userDTO()).when(userService).update(Mockito.any(UserDTO.class));
        UserDTO responseBody = webTestClient.put()
                .uri("/users")
                .body(BodyInserters.fromPublisher(Mono.just(userDTO()), UserDTO.class))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(ID, responseBody.getId());
    }
    private User user(){
        User user = new User();
        user.setId(ID);
        user.setMail(MAIL);
        user.setPassword(PASS);
        user.setNotifications(NOTIFY);
        user.setRoles(List.of(ROLE));
        user.setVersion(VERSION);
        return user;
    }
    private UserDTO userDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setMail(MAIL);
        userDTO.setPassword(PASS);
        userDTO.setNotifications(NOTIFY);
        userDTO.setRoles(List.of(ROLE));
        userDTO.setVersion(VERSION);
        return userDTO;
    }

}
