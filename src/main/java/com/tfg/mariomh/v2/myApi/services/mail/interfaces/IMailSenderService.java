package com.tfg.mariomh.v2.myApi.services.mail.interfaces;

import com.tfg.mariomh.v2.myApi.models.dtos.VerifyCodeDTO;

public interface IMailSenderService {
    VerifyCodeDTO sendVerifyMail(String mail);
    Boolean sendNewPasswordMail(String mail);
    void sendNewGameAvailableMails();

}
