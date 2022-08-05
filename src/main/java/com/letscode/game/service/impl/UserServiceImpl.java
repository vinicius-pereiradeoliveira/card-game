package com.letscode.game.service.impl;

import com.letscode.game.entity.UserEntity;
import com.letscode.game.repository.UserRepository;
import com.letscode.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Long createPlayer(String name, String password){

        UserEntity user = new UserEntity();

        user.setName(name);
        user.setPoints(0);
        user.setSessionActive(false);
        user.setQuizzes(0);
        user.setPassword(encoder.encode(password));

        userRepository.save(user);
        return userRepository.findByName(name).get().getId();

    }

    public Optional<UserEntity> login(String name){
        return userRepository.findByName(name);
    }

    public void activeSession(boolean validPass, String name) {
        if(validPass){
            Optional<UserEntity> user = userRepository.findByName(name);
            user.get().setSessionActive(true);
            userRepository.save(user.get());
        }
    }

    public Boolean validateSessionActive(long userId){
        return userRepository.findById(userId).get().getSessionActive();
    }

}
