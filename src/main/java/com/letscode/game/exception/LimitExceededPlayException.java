package com.letscode.game.exception;

public class LimitExceededPlayException extends RuntimeException{
    public LimitExceededPlayException(){
        super("Rodada encerrada! Inicie nova.");
    }

}
