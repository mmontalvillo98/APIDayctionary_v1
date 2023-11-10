package com.tfg.mariomh.v2.myApi.controllers.interfaces;

import com.tfg.mariomh.v2.myApi.models.dtos.VerifyCodeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("mails")
public interface IMailController {

    @GetMapping("/verify/{mail}")
    ResponseEntity<VerifyCodeDTO> verify(@PathVariable String mail);
    @GetMapping("/forgot/{mail}")
    ResponseEntity<Boolean> forgotPassword(@PathVariable String mail);

}
