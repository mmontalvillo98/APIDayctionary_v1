package com.tfg.mariomh.v2.myApi.controllers.implementations;

import com.tfg.mariomh.v2.myApi.models.entities.SynonymSingle;
import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.SynonymServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mini/synonyms")
public class SynonymControllerImpl extends MiniGameController<SynonymSingle, SynonymServiceImpl> {
    public SynonymControllerImpl(SynonymServiceImpl miniGameService) {
        super(miniGameService);
    }
}
