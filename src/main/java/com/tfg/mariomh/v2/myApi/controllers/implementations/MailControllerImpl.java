package com.tfg.mariomh.v2.myApi.controllers.implementations;

import com.tfg.mariomh.v2.myApi.controllers.interfaces.IMailController;
import com.tfg.mariomh.v2.myApi.models.dtos.VerifyCodeDTO;
import com.tfg.mariomh.v2.myApi.services.mail.interfaces.IMailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MailControllerImpl implements IMailController {

    private final IMailSenderService mailService;

    @Override
    public ResponseEntity<VerifyCodeDTO> verify(String mail) {
        return new ResponseEntity<>(mailService.sendVerifyMail(mail), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Boolean> forgotPassword(String mail) {
        return new ResponseEntity<>(mailService.sendNewPasswordMail(mail), HttpStatus.OK);
    }

}
