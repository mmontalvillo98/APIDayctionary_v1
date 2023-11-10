package com.tfg.mariomh.v2.myApi.controllers.implementations;

import com.tfg.mariomh.v2.myApi.controllers.interfaces.IAnswerController;
import com.tfg.mariomh.v2.myApi.models.dtos.AnswerDTO;
import com.tfg.mariomh.v2.myApi.models.dtos.GameAnswerDTO;
import com.tfg.mariomh.v2.myApi.services.bbdd.interfaces.IAnswerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AnswerControllerImpl implements IAnswerController {
    private final IAnswerService answerService;
    @Override
    public ResponseEntity<AnswerDTO> answerGame(GameAnswerDTO gameAnswerDTO) {
        return new ResponseEntity<>(answerService.answerGame(gameAnswerDTO), HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<AnswerDTO> getAnswerByUserGame(String userId, String gameId) {
        return new ResponseEntity<>(answerService.getAnswerByUserGame(userId, gameId), HttpStatus.OK);
    }
}
