package com.tfg.mariomh.v2.myApi.models.daos;

import com.tfg.mariomh.v2.myApi.models.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserDao  extends MongoRepository<User, String> {

    final static String FIND_ALL = "{deleted: false}";
    final static String FIND_BY_ID = "{id: '?0', deleted: false}";
    final static String FIND_BY_MAIL = "{mail: '?0', deleted: false}";
    final static String FIND_WANT_NOTIFICATION = "{notifications: true, deleted: false}";
    @Query(FIND_ALL)
    List<User> findAll();
    @Query(FIND_BY_ID)
    Optional<User> findById(String id);
    @Query(FIND_BY_MAIL)
    Optional<User> findByMail(String mail);
    @Query(FIND_WANT_NOTIFICATION)
    List<User> findAllWantNotifications();

}
