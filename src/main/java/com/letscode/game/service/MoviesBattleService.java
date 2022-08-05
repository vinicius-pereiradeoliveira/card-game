package com.letscode.game.service;

import com.letscode.game.dto.RankingDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MoviesBattleService {

    void startGame(Long userId);

    List<String> buildNewQuiz(Long userId);

    Boolean answerMovieBattle(String chosenMovie, String anotherMovie, Long userId);

    List<String> recoverAndAnswerLastQuiz(Long userId);

    List<RankingDto> getGameRanking();
}
