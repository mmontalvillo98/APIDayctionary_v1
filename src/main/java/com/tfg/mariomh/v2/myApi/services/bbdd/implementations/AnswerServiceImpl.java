package com.tfg.mariomh.v2.myApi.services.bbdd.implementations;

import com.tfg.mariomh.v2.myApi.exceptions.responses.*;
import com.tfg.mariomh.v2.myApi.mappers.interfaces.IAnswerMapper;
import com.tfg.mariomh.v2.myApi.models.daos.AnswerDao;
import com.tfg.mariomh.v2.myApi.models.daos.GameDao;
import com.tfg.mariomh.v2.myApi.models.daos.UserDao;
import com.tfg.mariomh.v2.myApi.models.dtos.AnswerDTO;
import com.tfg.mariomh.v2.myApi.models.dtos.GameAnswerDTO;
import com.tfg.mariomh.v2.myApi.models.entities.GameAnswer;
import com.tfg.mariomh.v2.myApi.types.MiniGame;
import com.tfg.mariomh.v2.myApi.models.entities.Answer;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import com.tfg.mariomh.v2.myApi.models.entities.User;
import com.tfg.mariomh.v2.myApi.services.bbdd.interfaces.IAnswerService;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements IAnswerService {

    private final GameDao gameDao;
    private final UserDao userDao;
    private final IAnswerMapper answerMapper;
    private final AnswerDao answerDao;

    /**
     * Tras validar todos los campos y la existencia del usuario y el juego se busca si el usuario ha contestado ya algún apartado de ese juego.
     * En caso afirmativo se añade una nueva contestación a partir de los datos facilitados a esa respuesta.
     * En caso negativo se crea una nueva respuesta y se añade la información facilitada.
     * @param gameAnswerDTO contestación de un usuario para un minijuego.
     * @return AnswerDTO lista de todas las respuestas esas para ese juego por el usuario incluyendo la nueva.
     */
    @Transactional
    @Override
    public AnswerDTO answerGame(GameAnswerDTO gameAnswerDTO) {
        validMinigame(gameAnswerDTO);
        validWord(gameAnswerDTO);
        User user = userDao.findById(gameAnswerDTO.getUserId()).orElseThrow(()->new UserNotFoundResponseException());
        Game game = gameDao.findById(gameAnswerDTO.getGameId()).orElseThrow(()->new GameNotFoundResponseException());
        Optional<Answer> answerBd = answerDao.findAnswerByUserGame(gameAnswerDTO.getUserId(), gameAnswerDTO.getGameId());
        if(answerBd.isPresent()){
            return answerMapper.answerToAnswerDTO(update(answerBd.get(), gameAnswerDTO, user, game));
        }else{
            return answerMapper.answerToAnswerDTO(create(gameAnswerDTO, user, game));
        }
    }

    /**
     * Valida que el usuario no haya contestado ya ese minijuego y añade la nueva respuesta a la lista de contestaciones de ese juego.
     * @param answerBd respuestas del usuario
     * @param gameAnswerDTO nueva respuesta del usuario
     * @param user usuario que realiza la respuesta
     * @param game juego al que se está jugando
     * @return Answer Toda la información relacionada con esas respuestas
     */
    private Answer update(Answer answerBd, GameAnswerDTO gameAnswerDTO, User user, Game game){
        answerable(answerBd, gameAnswerDTO);
        answerBd.getGameAnswers().add(new GameAnswer(gameAnswerDTO.getMiniGame(), gameAnswerDTO.getWord()));
        answerBd = answerDao.save(answerBd);
        return answerBd;
    }

    /**
     * Crea un nuevo objeto Answer para un usuario y un juego y lo añade al usuario y al juego.
     * @param gameAnswerDTO respuestas del usuario
     * @param user usuario que realiza la respuesta
     * @param game juego que está siendo jugado
     * @return Answer nuevo objeto
     */
    private Answer create(GameAnswerDTO gameAnswerDTO, User user, Game game){
        Answer answer = new Answer();
        answer.setUser(user);
        answer.setGame(game);
        answer.setGameAnswers(new ArrayList<>());
        answer.getGameAnswers().add(new GameAnswer(gameAnswerDTO.getMiniGame(), gameAnswerDTO.getWord()));
        answer = answerDao.save(answer);
        user.getAnswers().add(answer);
        game.getAnswers().add(answer);
        userDao.save(user);
        gameDao.save(game);
        return answer;
    }

    /**
     * Encuentra las respuestas para un juego de un usuario
     * @param userId id del usuario
     * @param gameId id del juego
     * @return AnswerDTO objeto con todas las respuestas del usuario para ese juego
     */
    @Override
    public AnswerDTO getAnswerByUserGame(String userId, String gameId) {
        return answerMapper.answerToAnswerDTO(answerDao.findAnswerByUserGame(userId, gameId)
                .orElseThrow(()->new AnswerNotFoundResponseException()));
    }

    /**
     * Valida que si un minijuego ya ha sido contestado
     * @param answerBd todas las respuestas hechas por un usuario
     * @param newGameAnswer respuesta nueva
     */
    private void answerable(Answer answerBd, GameAnswerDTO newGameAnswer){
        if(answerBd.getGameAnswers().stream()
                .filter(gameAnswer->gameAnswer.getMiniGame()==newGameAnswer.getMiniGame())
                .findAny().isPresent()){
            throw new AnswerDoneResponseException();
        }
    }

    /**
     * Valida que un GameAnswerDTO tiene un minijuego válido
     * @param gameAnswerDTO objeto con minijuego
     */
    private void validMinigame(GameAnswerDTO gameAnswerDTO){
        if(!Validator.objectsNotNull(gameAnswerDTO.getMiniGame())){
            throw new ObjectWithoutParamResponseException(Answer.class, "miniGame", MiniGame.class);
        }
    }

    /**
     * Valida que un GameAnswerDTO tiene respuesta válida
     * @param gameAnswerDTO objeto con respuesta
     */
    private void validWord(GameAnswerDTO gameAnswerDTO){
        if(!Validator.objectsValid(gameAnswerDTO.getWord())){
            throw new ObjectWithoutParamResponseException(Answer.class, "word", String.class);
        }
    }

}
