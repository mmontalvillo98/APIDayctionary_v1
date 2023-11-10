package com.tfg.mariomh.v2.myApi.services.mail.implementations;

import com.tfg.mariomh.v2.myApi.models.entities.User;
import com.tfg.mariomh.v2.myApi.types.Mail;
import com.tfg.mariomh.v2.myApi.services.mail.interfaces.IMailCreatorService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MailCreatorServiceImpl implements IMailCreatorService {

    private final static String MY_PAGE = "dayctionary.com";
    private final static String MY_MAIL = "m.montalvillo98@gmail.com";

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    /**
     * Crea un mensaje a partir de la información facilitada.
     * @param toEmail destinatario del email
     * @param subject asunto del email
     * @param template ruta a la plantilla thymeleaf
     * @param context conjunto de datos enviados a la plantilla
     * @return MimeMessage email
     * @throws MessagingException
     */
    private MimeMessage createThymeleafMailWith(String toEmail, String subject, String template, IContext context) throws MessagingException{
        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        final String htmlContent = this.templateEngine.process(template, context);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(htmlContent, true);
        return mimeMessage;
    }

    /**
     * Crea un email a partir de un enum Mail, un destinatario y un context.
     * @param mail Conjunto de datos del email a enviar
     * @param toMail email destinatario
     * @param context información que transmitir a la plantilla thymeleaf
     * @return MimeMessage email
     * @throws MessagingException
     */
    private MimeMessage createThymeleafMailWithMail(Mail mail, String toMail, Context context) throws MessagingException {
        context.setVariable("title", MY_PAGE);
        context.setVariable("action", mail.getAction());
        return createThymeleafMailWith(toMail, mail.getSubject(), mail.getTemplate(), context);
    }

    /**
     * Crea un email para verificar que una cuenta pertenece a un usuario con un código facilitado.
     * @param mail email destinatario
     * @param code código de verificación
     * @return MimeMessage email
     * @throws MessagingException
     */
    @Override
    public MimeMessage verifyUserMail(String mail, String code) throws MessagingException {
        final Context context = createContext();
        context.setVariable("code", code);
        return createThymeleafMailWithMail(Mail.VERIFICATION, mail, context);
    }

    /**
     * Crea un email informando de su nueva contraseña a un usuario.
     * @param mail email destinatario
     * @param code nueva contraseña
     * @return MimeMessage email
     * @throws MessagingException
     */
    @Override
    public MimeMessage newPasswordMail(String mail, String code) throws MessagingException {
        final Context context = createContext();
        context.setVariable("code", code);
        return createThymeleafMailWithMail(Mail.PASSWORD, mail, context);
    }

    /**
     * Crea un email que informa a un usuario de que hay disponible un nuevo juego en la página.
     * @param user usuario destinatario
     * @return MimeMessage email
     * @throws MessagingException
     */
    @Override
    public MimeMessage newGameAvailableMail(User user) throws MessagingException {
        final Context context = createContext();
        context.setVariable("mail", user.getMail());
        return createThymeleafMailWithMail(Mail.AVAILABLE, user.getMail(), context);
    }

    /**
     * Crea un context con la localización española.
     * @return Context contenedor de variables que enviar a plantilla thymeleaf
     */
    private Context createContext(){
        return new Context(new Locale("gl","ES"));
    }

}
