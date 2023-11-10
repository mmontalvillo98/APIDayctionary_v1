package com.tfg.mariomh.v2.myApi.controllers.interfaces;

import com.tfg.mariomh.v2.myApi.models.dtos.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
public interface IUserController {

    @GetMapping("/token/{id}")
    ResponseEntity<UserDTO> getById(@PathVariable String id);
    @PostMapping("")
    ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO);
    @PutMapping("")
    ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO);
    @DeleteMapping("/{userId}/{version}")
    ResponseEntity<Boolean> deleteById(@PathVariable String userId, @PathVariable Long version);
    @GetMapping("/stopNotifications/{mail}")
    ResponseEntity<Boolean> stopNotifications(@PathVariable String mail);
    @GetMapping("/{mail}")
    ResponseEntity<Boolean> usedMail(@PathVariable String mail);

}
