package com.letscode.game.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="battles")
@Data
@NoArgsConstructor
public class BattleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer count;

    private Integer answers;

    private Integer errors;

    private String lastPair;

    private Integer playCount;

    private Long userId;

}
