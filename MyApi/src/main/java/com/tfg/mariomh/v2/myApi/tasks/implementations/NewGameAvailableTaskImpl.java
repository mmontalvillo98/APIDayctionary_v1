package com.tfg.mariomh.v2.myApi.tasks.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.responses.GameNotFoundWithDateResponseException;
import com.tfg.mariomh.v2.myApi.exceptions.responses.MailNotSendException;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.services.bbdd.implementations.GameServiceImpl;
import com.tfg.mariomh.v2.myApi.services.mail.interfaces.IMailSenderService;
import com.tfg.mariomh.v2.myApi.tasks.interfaces.ITask;
import com.tfg.mariomh.v2.myApi.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log
@AllArgsConstructor
public class NewGameAvailableTaskImpl implements ITask {

    private final IMailSenderService mailService;
    private final GameServiceImpl gameService;

    @Scheduled(cron = DO_MIDNIGHT)
    public void process() {
        log.info("Initializing new Game available process...");
        try {
            Game game = gameService.getDateWord(DateUtil.getTodayDate());
            mailService.sendNewGameAvailableMails();
            log.info("New game available process ended successfully");
        }catch(GameNotFoundWithDateResponseException e){
            log.info("There is no new game available today");
        }catch(MailNotSendException e){
            log.info("An error occurred during the sending of mails");
        }
    }
}
