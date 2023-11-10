package com.tfg.mariomh.v2.myApi.controllers;

import com.tfg.mariomh.v2.myApi.models.dtos.VerifyCodeDTO;
import com.tfg.mariomh.v2.myApi.services.mail.implementations.MailSenderServiceImpl;
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
public class MailControllerTest {
    private static final String MAIL = "m.montalvillo98@gmail.com";
    private static final String CODE = "C0d3";
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    MailSenderServiceImpl mailService;

    @Test
    public void verifyMailTest(){
        ParameterizedTypeReference<VerifyCodeDTO> typeRef =new ParameterizedTypeReference<VerifyCodeDTO>() {};
        Mockito.doReturn(verifyCodeDTO()).when(mailService).sendVerifyMail(Mockito.anyString());
        VerifyCodeDTO verifyCodeDTO = webTestClient.get()
                .uri("/mails/verify/{mail}", MAIL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(verifyCodeDTO);
        Assertions.assertEquals(CODE, verifyCodeDTO.getCode());
    }
    @Test
    public void forgotPasswordTest(){
        ParameterizedTypeReference<Boolean> typeRef =new ParameterizedTypeReference<Boolean>() {};
        Mockito.doReturn(Boolean.TRUE).when(mailService).sendNewPasswordMail(Mockito.anyString());
        Boolean response = webTestClient.get()
                .uri("/mails/forgot/{mail}", MAIL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(typeRef)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Boolean.TRUE, response);
    }

    private VerifyCodeDTO verifyCodeDTO(){
        return new VerifyCodeDTO(CODE);
    }

}
