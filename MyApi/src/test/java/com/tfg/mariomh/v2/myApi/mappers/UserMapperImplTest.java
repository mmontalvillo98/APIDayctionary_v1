package com.tfg.mariomh.v2.myApi.mappers;

import com.tfg.mariomh.v2.myApi.mappers.implementations.UserMapperImpl;
import com.tfg.mariomh.v2.myApi.models.dtos.UserDTO;
import com.tfg.mariomh.v2.myApi.models.entities.User;
import com.tfg.mariomh.v2.myApi.types.Role;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperImplTest {
    private final static String ID = "ID";
    private final static String MAIL = "MAIL";
    private final static String NEW_MAIL = "NEW_MAIL";
    private final static String PASS = "PASS";
    private final static String NEW_PASS = "NEW_PASS";
    private final static Boolean NOTIFY = Boolean.TRUE;
    private final static Boolean NEW_NOTIFY = Boolean.FALSE;
    private final static Long VERSION = 0L;
    private final static Role ROLE = Role.USER;
    @InjectMocks
    private UserMapperImpl userMapper;

    @BeforeEach
    public void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void updateUserTest(){
        User response = userMapper.updateUser(user(), newUserDTO());
        User expected = newUser();
        Assertions.assertEquals(expected.getId(), response.getId());
        Assertions.assertEquals(expected.getMail(), response.getMail());
        Assertions.assertTrue(new BCryptPasswordEncoder().matches(expected.getPassword(), response.getPassword()));
        Assertions.assertEquals(expected.getNotifications(), response.getNotifications());
        Assertions.assertEquals(expected.getRoles().size(), response.getRoles().size());
        for(int i = 0; i<expected.getRoles().size(); i++){
            Assertions.assertEquals(expected.getRoles().get(i), response.getRoles().get(i));
        }
        Assertions.assertEquals(expected.getVersion(), response.getVersion());
    }
    @Test
    public void userToUserDTOWithoutPasswordTest(){
        UserDTO response = userMapper.userToUserDTOWithoutPassword(user());
        UserDTO expected = userDTO();
        Assertions.assertEquals(expected.getId(), response.getId());
        Assertions.assertEquals(expected.getMail(), response.getMail());
        Assertions.assertEquals(null, response.getPassword());
        Assertions.assertEquals(expected.getNotifications(), response.getNotifications());
        Assertions.assertEquals(expected.getRoles().size(), response.getRoles().size());
        for(int i = 0; i<expected.getRoles().size(); i++){
            Assertions.assertEquals(expected.getRoles().get(i), response.getRoles().get(i));
        }
        Assertions.assertEquals(expected.getVersion(), response.getVersion());
    }
    @Test
    public void userToUserDTOTest(){
        UserDTO response = userMapper.userToUserDTO(user());
        UserDTO expected = userDTO();
        Assertions.assertEquals(expected.getId(), response.getId());
        Assertions.assertEquals(expected.getMail(), response.getMail());
        Assertions.assertEquals(expected.getPassword(), response.getPassword());
        Assertions.assertEquals(expected.getNotifications(), response.getNotifications());
        Assertions.assertEquals(expected.getRoles().size(), response.getRoles().size());
        for(int i = 0; i<expected.getRoles().size(); i++){
            Assertions.assertEquals(expected.getRoles().get(i), response.getRoles().get(i));
        }
        Assertions.assertEquals(expected.getVersion(), response.getVersion());
    }
    @Test
    public void userDTOToUserTest(){
        User response = userMapper.userDTOToUser(userDTO());
        User expected = user();
        Assertions.assertEquals(expected.getId(), response.getId());
        Assertions.assertEquals(expected.getMail(), response.getMail());
        Assertions.assertEquals(expected.getPassword(), response.getPassword());
        Assertions.assertEquals(expected.getNotifications(), response.getNotifications());
        Assertions.assertEquals(expected.getRoles().size(), response.getRoles().size());
        for(int i = 0; i<expected.getRoles().size(); i++){
            Assertions.assertEquals(expected.getRoles().get(i), response.getRoles().get(i));
        }
        Assertions.assertEquals(expected.getVersion(), response.getVersion());
    }
    @Test
    public void userListToUserDTOListTest(){
        List<UserDTO> response = List.of(userMapper.userToUserDTOWithoutPassword(user()));
        List<UserDTO> expected = List.of(userDTO());
        for(int i = 0; i<expected.size(); i++) {
            Assertions.assertEquals(expected.get(i).getId(), response.get(i).getId());
            Assertions.assertEquals(expected.get(i).getMail(), response.get(i).getMail());
            Assertions.assertEquals(null, response.get(i).getPassword());
            Assertions.assertEquals(expected.get(i).getNotifications(), response.get(i).getNotifications());
            Assertions.assertEquals(expected.get(i).getRoles().size(), response.get(i).getRoles().size());
            for (int j = 0; j < expected.get(i).getRoles().size(); j++) {
                Assertions.assertEquals(expected.get(i).getRoles().get(j), response.get(i).getRoles().get(j));
            }
            Assertions.assertEquals(expected.get(i).getVersion(), response.get(i).getVersion());
        }
    }

    private User user(){
        User user = new User();
        user.setId(ID);
        user.setMail(MAIL);
        user.setPassword(PASS);
        user.setNotifications(NOTIFY);
        user.setRoles(List.of(ROLE));
        user.setVersion(VERSION);
        return user;
    }
    private User newUser(){
        User user = new User();
        user.setId(ID);
        user.setMail(NEW_MAIL);
        user.setPassword(NEW_PASS);
        user.setNotifications(NEW_NOTIFY);
        user.setRoles(List.of(ROLE));
        user.setVersion(VERSION);
        return user;
    }
    private UserDTO userDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setMail(MAIL);
        userDTO.setPassword(PASS);
        userDTO.setNotifications(NOTIFY);
        userDTO.setRoles(List.of(ROLE));
        userDTO.setVersion(VERSION);
        return userDTO;
    }
    private UserDTO newUserDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setMail(NEW_MAIL);
        userDTO.setPassword(NEW_PASS);
        userDTO.setNotifications(NEW_NOTIFY);
        userDTO.setRoles(List.of(ROLE));
        userDTO.setVersion(VERSION);
        return userDTO;
    }
}
