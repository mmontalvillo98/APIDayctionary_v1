package com.tfg.mariomh.v2.myApi.services.bbdd.interfaces;

import com.tfg.mariomh.v2.myApi.models.dtos.UserDTO;

public interface IUserService {

    UserDTO getByIdInfoUser(String id);
    UserDTO getByMailCompleteUser(String mail);
    UserDTO save(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
    Boolean deleteById(String userId, Long version);
    Boolean usedMail(String mail);
    Boolean stopNotifications(String mail);
}
