package com.tfg.mariomh.v2.myApi.controllers.implementations;

import com.tfg.mariomh.v2.myApi.models.entities.DefinitionSingle;
import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.DefinitionServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mini/definitions")
public class DefinitionControllerImpl extends MiniGameController<DefinitionSingle, DefinitionServiceImpl> {

    protected DefinitionControllerImpl(DefinitionServiceImpl service) {
        super(service);
    }
}
