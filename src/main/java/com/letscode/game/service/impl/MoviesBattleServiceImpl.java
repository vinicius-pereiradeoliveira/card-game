package com.letscode.game.service.impl;

import com.letscode.game.dto.MoviesDto;
import com.letscode.game.dto.RankingDto;
import com.letscode.game.entity.BattleEntity;
import com.letscode.game.entity.MovieEntity;
import com.letscode.game.entity.UserEntity;
import com.letscode.game.exception.LimitErrorsExceededException;
import com.letscode.game.exception.LimitExceededPlayException;
import com.letscode.game.exception.NotAnswerLastQuizException;
import com.letscode.game.repository.BattleRepository;
import com.letscode.game.repository.MoviesRepository;
import com.letscode.game.repository.UserRepository;
import com.letscode.game.service.MoviesBattleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MoviesBattleServiceImpl implements MoviesBattleService {

    @Autowired
    private  BattleRepository battleRepository;

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private UserRepository userRepository;

    public void startGame(Long userId) {

        BattleEntity createBattle = new BattleEntity();

        createBattle.setUserId(userId);
        createBattle.setCount(0);
        createBattle.setPlayCount(0);
        createBattle.setErrors(0);
        createBattle.setAnswers(0);
        createBattle.setLastPair("");

        battleRepository.save(createBattle);
    }
    public List<String> buildNewQuiz(Long userId){

        Optional<BattleEntity> battleToValidate = battleRepository.findByUserId(userId);
        Optional<UserEntity> userSession = userRepository.findById(userId);

        if (battleToValidate.isPresent() && battleToValidate.get().getPlayCount() > 5){
            battleRepository.delete(battleToValidate.get());
            userSession.get().setSessionActive(false);
            userRepository.save(userSession.get());
            throw new LimitExceededPlayException();
        }

        List<MoviesDto> moviesToBattle = sortMoviesToBattle(mapEntityToDto(moviesRepository.findAll()));
        return validateMovies(moviesToBattle,userId);
    }

    public Boolean answerMovieBattle(String chosenMovie, String anotherMovie, Long userId){

        MovieEntity movieAnswer = moviesRepository.findByTitle(chosenMovie);
        MovieEntity movieAnotherOption = moviesRepository.findByTitle(anotherMovie);
        Optional<BattleEntity> currentBattle = battleRepository.findByUserId(userId);

        if(currentBattle.get().getCount() > currentBattle.get().getAnswers()+1){
            throw new NotAnswerLastQuizException();
        }

        return validateAnswers(movieAnswer, movieAnotherOption, currentBattle, userId);

    }

    public List<String> recoverAndAnswerLastQuiz(Long userId){

        Optional<BattleEntity> battleToValidate = battleRepository.findByUserId(userId);
        String lastQuizMovies = battleToValidate.get().getLastPair();

        String[] strArrayMovies = lastQuizMovies.split("-");

        List<String> lastMovies = new ArrayList<>();
        lastMovies.add(strArrayMovies[0]);
        lastMovies.add(strArrayMovies[1]);

        battleToValidate.get().setCount(battleToValidate.get().getAnswers());
        battleRepository.save(battleToValidate.get());

        return lastMovies;
    }

    public List<RankingDto> getGameRanking() {

        List<RankingDto> ranking = new ArrayList<>();
        AtomicReference<Double> finalPoints = new AtomicReference<>();

        userRepository.findAll().forEach(item->{

            int quizzesNumber = item.getQuizzes();
            int points = item.getPoints();

            if(quizzesNumber > 0){
                Double pointsPercent = Double.valueOf((points * 100)/quizzesNumber);
                finalPoints.set(quizzesNumber * pointsPercent);

                ranking.add(RankingDto.builder()
                        .name(item.getName())
                        .rate(finalPoints.get())
                        .build());
            }

        });

        ranking.sort(Comparator.comparingDouble(RankingDto::getRate).reversed());
        return ranking;
    }

    private List<String> validateMovies(List<MoviesDto> moviesToBattle, Long userId) {

        Optional<BattleEntity> battle = battleRepository.findByUserId(userId);

        String currentMoviePair = moviesToBattle.get(0).getTitle()
                +"-"+moviesToBattle.get(1).getTitle();

        String currentMoviePairReverse = moviesToBattle.get(1).getTitle()
                +"-"+moviesToBattle.get(0).getTitle();

        if(!moviesToBattle.get(0).getTitle().equals(moviesToBattle.get(1).getTitle())) {

            if(battle.get().getLastPair().equals(currentMoviePair) || battle.get().getLastPair().equals(currentMoviePairReverse)) {
                buildNewQuiz(userId);
            } else {
                battle.get().setCount(battle.get().getCount()+1);
                battle.get().setPlayCount(battle.get().getPlayCount()+1);
                battle.get().setLastPair(currentMoviePair);
                battleRepository.save(battle.get());
            }

            return Arrays.asList(moviesToBattle.get(0).getTitle(),moviesToBattle.get(1).getTitle());

        } else {
            return buildNewQuiz(userId);
        }
    }

    private List<MoviesDto> mapEntityToDto(List<MovieEntity> movies){

        List<MoviesDto> moviesDto = new ArrayList<>();

        movies.forEach(item->{
            moviesDto.add(MoviesDto.builder()
                    .title(item.getTitle())
                    .rate(item.getRate())
                    .build());
        });

        return moviesDto;

    }

    private List<MoviesDto> sortMoviesToBattle(List<MoviesDto> movies){

        Random rand = new Random();

        int firstRandomMovie = rand.nextInt(movies.size());
        int secondRandomMovie = rand.nextInt(movies.size());

        return Arrays.asList(movies.get(firstRandomMovie),
                movies.get(secondRandomMovie));
    }

    private Boolean validateAnswers(MovieEntity movieAnswer, MovieEntity anotherMovie,
                                    Optional<BattleEntity> currentBattle, Long userId) {

        Optional<UserEntity> user = userRepository.findById(userId);

        if(movieAnswer.getPoints().compareTo(anotherMovie.getPoints()) == 1){

            currentBattle.get().setAnswers(currentBattle.get().getAnswers()+1);
            user.get().setQuizzes(user.get().getQuizzes()+1);
            user.get().setPoints(user.get().getPoints()+1);
            battleRepository.save(currentBattle.get());
            userRepository.save(user.get());

            return true;

        } else if (currentBattle.get().getErrors() == 3){
            battleRepository.delete(currentBattle.get());
            user.get().setSessionActive(false);
            userRepository.save(user.get());
            throw new LimitErrorsExceededException();

        } else {

            currentBattle.get().setErrors(currentBattle.get().getErrors()+1);
            currentBattle.get().setAnswers(currentBattle.get().getAnswers()+1);
            battleRepository.save(currentBattle.get());

            return false;
        }
    }

}
