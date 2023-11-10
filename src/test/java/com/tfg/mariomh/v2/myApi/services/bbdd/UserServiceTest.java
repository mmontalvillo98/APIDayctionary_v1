package com.tfg.mariomh.v2.myApi.services.bbdd;

import com.tfg.mariomh.v2.myApi.exceptions.responses.ObjectWithoutParamResponseException;
import com.tfg.mariomh.v2.myApi.exceptions.responses.UserUsedMailResponseException;
import com.tfg.mariomh.v2.myApi.mappers.interfaces.IUserMapper;
import com.tfg.mariomh.v2.myApi.models.daos.UserDao;
import com.tfg.mariomh.v2.myApi.models.dtos.UserDTO;
import com.tfg.mariomh.v2.myApi.models.entities.User;
import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.UserServiceImpl;
import com.tfg.mariomh.v2.myApi.types.Role;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private final static String ID = "ID";
    private final static String MAIL = "MAIL";
    private final static String PASS = "PASS";
    private final static Boolean NOTIFY = Boolean.TRUE;
    private final static Long VERSION = 0L;
    private final static Role ROLE = Role.USER;

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDao userDao;
    @Mock
    private IUserMapper userMapper;

    @BeforeEach
    public void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest(){
        UserDTO expected = userDTO();
        Mockito.doReturn(user()).when(userMapper).userDTOToUser(Mockito.any(UserDTO.class));
        Mockito.doReturn(user()).when(userDao).save(Mockito.any(User.class));
        Mockito.doReturn(expected).when(userMapper).userToUserDTOWithoutPassword(Mockito.any(User.class));
        Assertions.assertEquals(expected, userService.save(userDTO()));
    }
    @Test
    public void saveTestNoMail(){
        UserDTO userDTO = userDTO();
        userDTO.setMail(null);
        Assertions.assertThrows(ObjectWithoutParamResponseException.class, ()->userService.save(userDTO));
    }
    @Test
    public void saveTestUsedMail(){
        Mockito.doReturn(Optional.of(user())).when(userDao).findByMail(Mockito.anyString());
        Assertions.assertThrows(UserUsedMailResponseException.class, ()->userService.save(userDTO()));
    }
    @Test
    public void saveTestNoPass(){
        UserDTO userDTO = userDTO();
        userDTO.setPassword(null);
        Assertions.assertThrows(ObjectWithoutParamResponseException.class, ()->userService.save(userDTO));
    }
    @Test
    public void saveTestNoNotify(){
        UserDTO userDTO = userDTO();
        userDTO.setNotifications(null);
        Assertions.assertThrows(ObjectWithoutParamResponseException.class, ()->userService.save(userDTO));
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

}
