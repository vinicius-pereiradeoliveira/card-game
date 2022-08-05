package com.letscode.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerMovieBattleDto {

    private String chosenMovie;
    private String anotherMovie;

}
