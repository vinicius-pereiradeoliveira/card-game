package com.letscode.game.service;

import com.letscode.game.dto.RankingDto;
import com.letscode.game.entity.BattleEntity;
import com.letscode.game.entity.MovieEntity;
import com.letscode.game.entity.UserEntity;
import com.letscode.game.repository.BattleRepository;
import com.letscode.game.repository.MoviesRepository;
import com.letscode.game.repository.UserRepository;
import com.letscode.game.service.impl.MoviesBattleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class MoviesBattleServiceTest {

    @InjectMocks
    private MoviesBattleServiceImpl moviesBattleService;

    @Mock
    private BattleRepository battleRepository;

    @Mock
    private MoviesRepository moviesRepository;

    @Mock
    private UserRepository userRepository;

    private Optional<BattleEntity> battleToValidate;

    private Optional<UserEntity> userSession;

    private List<MovieEntity> movies;

    private Long id = 1L;

    private MovieEntity movie;

    private MovieEntity movie2;


    @BeforeEach
    public void setup() {

        battleToValidate = Optional.of(new BattleEntity());

        battleToValidate.get().setId(1L);
        battleToValidate.get().setAnswers(0);
        battleToValidate.get().setLastPair("");
        battleToValidate.get().setPlayCount(0);
        battleToValidate.get().setCount(0);
        battleToValidate.get().setErrors(0);
        battleToValidate.get().setUserId(1L);

        userSession = Optional.of(new UserEntity());

        userSession.get().setSessionActive(true);
        userSession.get().setId(1L);
        userSession.get().setPoints(0);
        userSession.get().setQuizzes(0);
        userSession.get().setPassword("12345");
        userSession.get().setName("Test1");

        movies = new ArrayList<>();

        movie = new MovieEntity(1L, "Filme Teste 1", 8.9, new BigDecimal(663452), new BigDecimal(1520000));
        movie2 = new MovieEntity(2L, "Filme Teste 2", 7.9, new BigDecimal(653452), new BigDecimal(1504000));

        movies.add(movie);
        movies.add(movie2);

    }

    @Test
    public void buildNewQuizTest() {

        List<String> moviesToCompare = Arrays.asList("Filme Teste 1","Filme Teste 2");

        Mockito.when(battleRepository.findByUserId(1L)).thenReturn(battleToValidate);
        Mockito.when(userRepository.findById(1L)).thenReturn(userSession);
        Mockito.when(moviesRepository.findAll()).thenReturn(movies);

        List<String> result = moviesBattleService.buildNewQuiz(1L);

        assertEquals(result.size(),moviesToCompare.size());
        assertTrue(validateQuizResult(result,moviesToCompare));

    }

    @Test
    public void answerMovieBattleTest() {

        String movieName1 = "Filme Teste 1";
        String movieName2 = "Filme Teste 2";

        List<String> moviesToCompare = Arrays.asList("Filme Teste 1","Filme Teste 2");

        Mockito.when(battleRepository.findByUserId(1L)).thenReturn(battleToValidate);
        Mockito.when(moviesRepository.findByTitle(movieName1)).thenReturn(movie);
        Mockito.when(moviesRepository.findByTitle(movieName2)).thenReturn(movie2);
        Mockito.when(userRepository.findById(1L)).thenReturn(userSession);

        boolean result = moviesBattleService.answerMovieBattle(movieName1,movieName2,1L);

        assertTrue(result);

    }

    @Test
    public void getGameRankingTest() {

        List<UserEntity> listToRanking = new ArrayList<>();

        UserEntity user1 = new UserEntity(32L,"Teste Ranking1","12345",1800, 520, true);
        UserEntity user2 = new UserEntity(33L,"Teste Ranking2","12345",1850, 250, true);
        UserEntity user3 = new UserEntity(34L,"Teste Ranking3","12345",2850, 1985, true);

        listToRanking.add(user1);
        listToRanking.add(user2);
        listToRanking.add(user3);

        Mockito.when(userRepository.findAll()).thenReturn(listToRanking);
        List<RankingDto> ranking = moviesBattleService.getGameRanking();

        assertEquals(ranking.get(0).getName(),"Teste Ranking3");
        assertEquals(ranking.get(2).getName(),"Teste Ranking2");

    }

    private boolean validateQuizResult(List<String> result, List<String> moviesToCompare){

        String resultPair = result.get(0).toString()+" - "+result.get(1).toString();
        String moviesToComparePair = moviesToCompare.get(0).toString()+" - "+moviesToCompare.get(1).toString();

        return resultPair.equals(moviesToComparePair) ? true : false;
    }

}
