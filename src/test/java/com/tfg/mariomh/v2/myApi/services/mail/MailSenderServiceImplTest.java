package com.tfg.mariomh.v2.myApi.services.mail;

import com.tfg.mariomh.v2.myApi.services.mail.implementations.MailSenderServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailSenderServiceImplTest {

    private final static String MY_MAIL = "m.montalvillo98@gmail.com";
    private final static String SECOND_MAIL = "m.montalzuelo@gmail.com";
    private final static String SCHOOL_MAIL = "mariomh@alumnos.iesgalileo.es";

    @Autowired
    private MailSenderServiceImpl mailSenderService;

    @Test
    public void sendVerifyMailTest(){
        Assertions.assertNotNull(mailSenderService.sendVerifyMail(MY_MAIL));
        Assertions.assertNotNull(mailSenderService.sendVerifyMail(SECOND_MAIL));
        Assertions.assertNotNull(mailSenderService.sendVerifyMail(SCHOOL_MAIL));
    }

    @Test
    public void sendNewGameAvailableMailsTest(){
        Assertions.assertDoesNotThrow(()->mailSenderService.sendNewGameAvailableMails());
    }

}
