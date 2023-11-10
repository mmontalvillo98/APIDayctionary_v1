package com.tfg.mariomh.v2.myApi.controllers.implementations;

import com.tfg.mariomh.v2.myApi.controllers.interfaces.IUserController;
import com.tfg.mariomh.v2.myApi.models.dtos.UserDTO;
import com.tfg.mariomh.v2.myApi.services.bbdd.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserControllerImpl implements IUserController {

    private final IUserService userService;

    @Override
    public ResponseEntity<UserDTO> getById(String id) {
        return new ResponseEntity<>(userService.getByIdInfoUser(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> save(UserDTO userDTO) {
        return new ResponseEntity<>(userService.save(userDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Boolean> stopNotifications(String mail) {
        return new ResponseEntity<>(userService.stopNotifications(mail), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> update(UserDTO userDTO) {
        return new ResponseEntity<>(userService.update(userDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> deleteById(String userId, Long version) {
        return new ResponseEntity<>(userService.deleteById(userId, version), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> usedMail(String mail) {
        return new ResponseEntity<>(userService.usedMail(mail), HttpStatus.OK);
    }

}
