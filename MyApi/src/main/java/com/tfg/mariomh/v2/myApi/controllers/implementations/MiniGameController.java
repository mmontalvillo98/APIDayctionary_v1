package com.tfg.mariomh.v2.myApi.controllers.implementations;

import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.MiniGameService;
import com.tfg.mariomh.v2.myApi.utils.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@AllArgsConstructor
public abstract class MiniGameController<T, S extends MiniGameService> {

    private final S miniGameService;

    @GetMapping("")
    ResponseEntity<List<T>> getTodayMiniGame(){
        return getDateMiniGame(DateUtil.getTodayDate());
    }

    @GetMapping("/{date}")
    ResponseEntity<List<T>> getDateMiniGame(@PathVariable String date){
        return new ResponseEntity<>(miniGameService.getList(date), HttpStatus.OK);
    }

}
