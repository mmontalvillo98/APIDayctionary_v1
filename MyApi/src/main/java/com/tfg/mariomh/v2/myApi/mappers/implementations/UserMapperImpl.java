package com.tfg.mariomh.v2.myApi.mappers.implementations;

import com.tfg.mariomh.v2.myApi.mappers.interfaces.IUserMapper;
import com.tfg.mariomh.v2.myApi.models.dtos.UserDTO;
import com.tfg.mariomh.v2.myApi.models.entities.User;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMapperImpl implements IUserMapper {
    @Override
    public User updateUser(User target, UserDTO source) {
        target.setId(source.getId());
        target.setVersion(source.getVersion());
        if(Validator.objectsNotNull(source.getPassword())){
            target.setPassword(new BCryptPasswordEncoder().encode(source.getPassword()));
        }
        target.setMail(source.getMail());
        target.setNotifications(source.getNotifications());
        return target;
    }

    @Override
    public UserDTO userToUserDTOWithoutPassword(User source) {
        UserDTO target = null;
        if(source!=null){
            target = new UserDTO();
            target.setId(source.getId());
            target.setMail(source.getMail());
            //target.setPassword(source.getPassword());
            target.setNotifications(source.getNotifications());
            target.setVersion(source.getVersion());
            target.setRoles(source.getRoles());
        }
        return target;
    }

    @Override
    public UserDTO userToUserDTO(User source) {
        UserDTO target = null;
        if(source!=null){
            target = new UserDTO();
            target.setId(source.getId());
            target.setMail(source.getMail());
            target.setPassword(source.getPassword());
            target.setNotifications(source.getNotifications());
            target.setVersion(source.getVersion());
            target.setRoles(source.getRoles());
        }
        return target;
    }

    @Override
    public User userDTOToUser(UserDTO source) {
        User target = null;
        if(source!=null){
            target = new User();
            target.setId(source.getId());
            target.setMail(source.getMail());
            target.setPassword(source.getPassword());
            target.setNotifications(source.getNotifications());
            target.setVersion(source.getVersion());
            target.setRoles(source.getRoles());
        }
        return target;
    }

    @Override
    public List<UserDTO> userListToUserDTOList(List<User> source) {
        List<UserDTO> target = null;
        if(source!=null){
            target = new ArrayList<>();
            for (User elem: source) {
                target.add(userToUserDTOWithoutPassword(elem));
            }
        }
        return target;
    }

}
