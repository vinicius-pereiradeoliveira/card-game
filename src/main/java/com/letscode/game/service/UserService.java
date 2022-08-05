package com.letscode.game.service;

import com.letscode.game.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Long createPlayer(String name, String password);

    Optional<UserEntity> login(String name);

    void activeSession(boolean validPass, String name);

    Boolean validateSessionActive(long userId);

}
