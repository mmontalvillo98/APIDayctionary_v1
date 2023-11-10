package com.tfg.mariomh.v2.myApi.services.bbdd.implementations;

import com.tfg.mariomh.v2.myApi.types.Role;
import com.tfg.mariomh.v2.myApi.exceptions.responses.ObjectWithoutParamResponseException;
import com.tfg.mariomh.v2.myApi.exceptions.responses.UserNotFoundResponseException;
import com.tfg.mariomh.v2.myApi.exceptions.responses.UserUsedMailResponseException;
import com.tfg.mariomh.v2.myApi.mappers.interfaces.IUserMapper;
import com.tfg.mariomh.v2.myApi.models.daos.UserDao;
import com.tfg.mariomh.v2.myApi.models.dtos.UserDTO;
import com.tfg.mariomh.v2.myApi.models.entities.User;
import com.tfg.mariomh.v2.myApi.services.bbdd.interfaces.IUserService;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserDao userDao;
    private final IUserMapper userMapper;

    /**
     * Devuelve toda la información de un usuario excepto su contraseña
     * @param id identificador único de usuario
     * @return UserDTO usuario
     */
    @Override
    public UserDTO getByIdInfoUser(String id){
        return userMapper.userToUserDTOWithoutPassword(userDao.findById(id).orElseThrow(()->new UserNotFoundResponseException()));

    }

    /**
     * Devuelve toda la información de un usuario
     * @param mail mail del usuario
     * @return UserDTO usuario completo
     */
    @Override
    public UserDTO getByMailCompleteUser(String mail) {
        return userMapper.userToUserDTO(userDao.findByMail(mail).orElseThrow(()->new UserNotFoundResponseException()));
    }

    /**
     * Registra a un usuario en la base de datos
     * @param userDTO nuevo usuario
     * @return usuario guardado
     */
    @Transactional
    @Override
    public UserDTO save(UserDTO userDTO) {
        validUserDTO(userDTO);
        User user = userMapper.userDTOToUser(userDTO);
        user.setNotifications(false);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(new ArrayList<>(List.of(Role.USER)));
        user.setAnswers(new ArrayList<>());
        return userMapper.userToUserDTOWithoutPassword(userDao.save(user));
    }

    /**
     * Actualiza los campos de un usuario.
     * @param userDTO usuario con nuevos datos.
     * @return usuario actualizado
     */
    @Transactional
    @Override
    public UserDTO update(UserDTO userDTO) {
        User userBd = userDao.findById(userDTO.getId()).orElseThrow(
                ()->new UserNotFoundResponseException()
        );
        if(!userBd.getMail().equals(userDTO.getMail())){
            validMail(UserDTO.class, userDTO.getMail());
        }
        return userMapper.userToUserDTOWithoutPassword(userDao.save(userMapper.updateUser(userBd, userDTO)));
    }

    /**
     * Elimina un usuario a través de su identificador y su versión
     * @param userId identificador único del usuario
     * @param version versión del usuario
     * @return Boolean
     */
    @Transactional
    @Override
    public Boolean deleteById(String userId, Long version) {
        User user = userDao.findById(userId).orElseThrow(()->new UserNotFoundResponseException());
        user.setVersion(version);// Incorrect -> throws exception
        user.setDeleted(Boolean.TRUE);
        userDao.save(user);
        return Boolean.TRUE;
    }

    /**
     * Verifica si un email está siendo usado por algún usuario de la aplicación
     * @param mail mail a verificar
     * @return Boolean
     */
    @Override
    public Boolean usedMail(String mail) {
        return userDao.findByMail(mail).isPresent();
    }

    /**
     * Hace que un usuario deje de recibir notificaciones de la aplicación.
     * @param mail email del usuario
     * @return True
     */
    @Transactional
    @Override
    public Boolean stopNotifications(String mail) {
        User user = userDao.findByMail(mail).orElseThrow(
                ()->new UserNotFoundResponseException()
        );
        user.setNotifications(Boolean.FALSE);
        userDao.save(user);
        return Boolean.TRUE;
    }

    private void validUserDTO(UserDTO userDTO) {
        validMail(UserDTO.class, userDTO.getMail());
        if(!Validator.objectsNotNull(userDTO.getPassword())){
            throw new ObjectWithoutParamResponseException(UserDTO.class, "password", String.class);
        }
        if(!Validator.objectsNotNull(userDTO.getNotifications())){
            throw new ObjectWithoutParamResponseException(UserDTO.class, "notifications", Boolean.class);
        }
    }

    private void validMail(Class clasz, String mail) {
        if(!Validator.objectsNotNull(mail)){
            throw new ObjectWithoutParamResponseException(clasz, "mail", String.class);
        }
        if(userDao.findByMail(mail).isPresent()){
            throw new UserUsedMailResponseException(mail);
        }
    }

}
