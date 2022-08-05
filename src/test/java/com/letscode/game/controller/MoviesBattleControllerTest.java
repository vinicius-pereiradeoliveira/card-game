package com.letscode.game.controller;

import com.letscode.game.service.MoviesBattleService;
import com.letscode.game.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(MoviesBattleController.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class MoviesBattleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MoviesBattleService moviesBattleService;

    @MockBean
    private UserControllerTest userControllerTest;

    @MockBean
    private UserService userService;

    final String BASE_PATH = "/movies";


    @Test
    public void newQuizWithoutLoginTest() throws Exception {

        mockMvc.perform(get(BASE_PATH+"/quiz/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void showRankingTest() throws Exception {

        mockMvc.perform(get(BASE_PATH+"/quiz/ranking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
