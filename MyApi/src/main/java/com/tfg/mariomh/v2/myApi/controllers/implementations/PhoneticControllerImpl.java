package com.tfg.mariomh.v2.myApi.controllers.implementations;

import com.tfg.mariomh.v2.myApi.models.entities.PhoneticSingle;
import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.PhoneticServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mini/phonetics")
public class PhoneticControllerImpl extends MiniGameController<PhoneticSingle, PhoneticServiceImpl> {
    public PhoneticControllerImpl(PhoneticServiceImpl miniGameService) {
        super(miniGameService);
    }
}
