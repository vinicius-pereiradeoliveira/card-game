package com.letscode.game.repository;

import com.letscode.game.entity.BattleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BattleRepository extends JpaRepository<BattleEntity, Long> {
    Optional<BattleEntity> findByUserId(Long userId);
}
