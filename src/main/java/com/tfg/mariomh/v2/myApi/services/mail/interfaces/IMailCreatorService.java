package com.tfg.mariomh.v2.myApi.services.mail.interfaces;

import com.tfg.mariomh.v2.myApi.models.entities.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public interface IMailCreatorService {

    MimeMessage verifyUserMail(String mail, String code)throws MessagingException;
    MimeMessage newPasswordMail(String mail, String code)throws MessagingException;
    MimeMessage newGameAvailableMail(User user)throws MessagingException;

}
