package com.tfg.mariomh.v2.myApi.services.mail.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.responses.MailNotSendException;
import com.tfg.mariomh.v2.myApi.exceptions.responses.UserNotFoundResponseException;
import com.tfg.mariomh.v2.myApi.models.daos.UserDao;
import com.tfg.mariomh.v2.myApi.models.dtos.VerifyCodeDTO;
import com.tfg.mariomh.v2.myApi.models.entities.User;
import com.tfg.mariomh.v2.myApi.services.mail.interfaces.IMailCreatorService;
import com.tfg.mariomh.v2.myApi.services.mail.interfaces.IMailSenderService;
import com.tfg.mariomh.v2.myApi.utils.CodeGeneratorUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements IMailSenderService {

    private final JavaMailSender javaMailSender;
    private final IMailCreatorService mailCreatorService;
    private final UserDao userDao;

    /**
     * Crea aleatoriamente un código y envía un email de verificación de cuenta a un usuario con ese código.
     * @param mail email cuya autoría validar
     * @return código generado aleatoriamente
     */
    @Async
    public VerifyCodeDTO sendVerifyMail(String mail){
        String code = CodeGeneratorUtil.generateCode();
        try{
            this.javaMailSender.send(mailCreatorService.verifyUserMail(mail, code));
        }catch(MessagingException e){
            throw new MailNotSendException();
        }
        return new VerifyCodeDTO(code);
    }

    /**
     * Genera un código aleatoriamente, crea y envía un email al usuario informándole del cambio de su contraseña y actualiza la contraseña del usuario con el código.
     * @param mail email del usuario que ha olvidado su contraseña
     * @return Boolean true si se realiza el proceso satisfactoriamente
     */
    @Transactional
    @Override
    public Boolean sendNewPasswordMail(String mail) {
        User user = userDao.findByMail(mail).orElseThrow(
                ()->new UserNotFoundResponseException()
        );
        String newPassword = CodeGeneratorUtil.generateCode();
        try{
            this.javaMailSender.send(mailCreatorService.newPasswordMail(mail, newPassword));
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userDao.save(user);
            return Boolean.TRUE;
        }catch(MessagingException e){
            throw new MailNotSendException();
        }
    }

    /**
     * Envía a todos los usuarios que tienen las notificaciones activas un mensaje avisando de que hay disponible un nuevo juego.
     */
    public void sendNewGameAvailableMails() {
        userDao.findAllWantNotifications().forEach(user -> {
            try {
                this.javaMailSender.send(mailCreatorService.newGameAvailableMail(user));
            } catch (MessagingException e) {
                throw new MailNotSendException();
            }
        });
    }

}
