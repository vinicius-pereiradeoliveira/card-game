package com.letscode.game.repository;

import com.letscode.game.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<MovieEntity, Long> {
    MovieEntity findByTitle(String movieName);
}
