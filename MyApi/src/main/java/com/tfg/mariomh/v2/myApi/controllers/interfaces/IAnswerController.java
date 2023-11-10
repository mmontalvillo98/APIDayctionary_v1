package com.tfg.mariomh.v2.myApi.controllers.interfaces;

import com.tfg.mariomh.v2.myApi.models.dtos.AnswerDTO;
import com.tfg.mariomh.v2.myApi.models.dtos.GameAnswerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/answers")
public interface IAnswerController {

    @PostMapping("/answer")
    ResponseEntity<AnswerDTO> answerGame(@RequestBody GameAnswerDTO gameAnswerDTO);

    @GetMapping("/answer/{userId}/{gameId}")
    ResponseEntity<AnswerDTO> getAnswerByUserGame(@PathVariable String userId, @PathVariable String gameId);

}
