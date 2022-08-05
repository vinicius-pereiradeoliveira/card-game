package com.letscode.game.scheduler;


import com.letscode.game.entity.UserEntity;
import com.letscode.game.repository.MoviesRepository;
import com.letscode.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@EnableScheduling
public class SchedulerTask {

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    private final long HOUR = 60 * 60 * 1000;

    @Scheduled(fixedDelay = HOUR)
    public void scheduleFutureTask() {

        // criptografa senha dos usuários criados na inicialização
        userRepository.findAll().forEach(item->{
            String passEncrypted = encoder.encode(item.getPassword());
            item.setPassword(passEncrypted);
            userRepository.save(item);
        });

        // calculo das notas de filmes
        // TODO - improve to update info
        moviesRepository.findAll().forEach(item->{
            BigDecimal rate = new BigDecimal(item.getRate());
            BigDecimal votes = item.getVotes();
            item.setPoints(rate.multiply(votes));
            moviesRepository.save(item);
        });

        System.out.println("Dados atualizados!");
    }

}
