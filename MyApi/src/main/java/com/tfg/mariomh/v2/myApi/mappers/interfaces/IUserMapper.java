package com.tfg.mariomh.v2.myApi.mappers.interfaces;

import com.tfg.mariomh.v2.myApi.models.dtos.UserDTO;
import com.tfg.mariomh.v2.myApi.models.entities.User;

import java.util.List;

public interface IUserMapper {

    User updateUser(User target, UserDTO source);
    UserDTO userToUserDTOWithoutPassword(User source);
    UserDTO userToUserDTO(User source);
    User userDTOToUser(UserDTO source);
    List<UserDTO> userListToUserDTOList(List<User> source);

}
