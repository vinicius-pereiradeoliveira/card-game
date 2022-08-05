package com.letscode.game.controller;

import com.letscode.game.dto.AnswerMovieBattleDto;
import com.letscode.game.dto.RankingDto;
import com.letscode.game.exception.LoginFailedException;
import com.letscode.game.service.MoviesBattleService;
import com.letscode.game.service.UserService;
import com.mchange.rmi.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MoviesBattleController {

    private final MoviesBattleService moviesBattleService;

    private final UserService userService;

    @PostMapping("/quiz/start/{id}")
    public void startNewGame(@PathVariable("id") long userId) throws NotAuthorizedException {
        if(userService.validateSessionActive(userId)) {
            moviesBattleService.startGame(userId);
        } else {
            throw new LoginFailedException();
        }

    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<List<String>> getNewQuiz(@PathVariable("id") long userId) throws NotAuthorizedException {

        if(userService.validateSessionActive(userId)) {
            return new ResponseEntity<>(moviesBattleService.buildNewQuiz(userId), HttpStatus.OK);
        } else {
            throw new LoginFailedException();
        }

    }

    @PostMapping("/quiz/answer/{id}")
    public ResponseEntity<Boolean> answerMovieBattle(@PathVariable("id") long userId, @RequestBody AnswerMovieBattleDto answer) throws NotAuthorizedException {

        if(userService.validateSessionActive(userId)) {
            return new ResponseEntity<>(moviesBattleService.answerMovieBattle(answer.getChosenMovie(),
                    answer.getAnotherMovie(), userId), HttpStatus.ACCEPTED);
        } else {
            throw new LoginFailedException();
        }

    }

    @GetMapping("/quiz/recover/{id}")
    public ResponseEntity<List<String>> recoverLastQuiz(@PathVariable("id") long userId) throws NotAuthorizedException {

        if(userService.validateSessionActive(userId)) {
            return new ResponseEntity<>(moviesBattleService.recoverAndAnswerLastQuiz(userId), HttpStatus.OK);
        } else {
            throw new LoginFailedException();
        }

    }

    @GetMapping("/quiz/ranking")
    public ResponseEntity<List<RankingDto>> showRanking(){
        return new ResponseEntity<>(moviesBattleService.getGameRanking(), HttpStatus.OK);
    }

}
