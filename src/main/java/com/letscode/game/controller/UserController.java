package com.letscode.game.controller;

import com.letscode.game.dto.UserDto;
import com.letscode.game.entity.UserEntity;
import com.letscode.game.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/user")
    public ResponseEntity<Long> createUser(@RequestBody UserDto user){
        return new ResponseEntity<>(userService.createPlayer(user.getName(), user.getPassword()), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String name, @RequestParam String password){

        Optional<UserEntity> player = userService.login(name);

        if(player.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado!");
        }

        boolean validPass = encoder.matches(password, player.get().getPassword());

        HttpStatus status = (validPass) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        userService.activeSession(validPass,name);

        return ResponseEntity.status(status).body("Usuário com ID "+player.get().getId().toString()+" - Autenticado com Sucesso!");
    }

}
