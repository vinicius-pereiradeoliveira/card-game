package com.letscode.game.exception;

public class LimitErrorsExceededException extends RuntimeException{
    public LimitErrorsExceededException(){
        super("Que pena, você errou mais de 3. Inicie nova rodada!");
    }
}
