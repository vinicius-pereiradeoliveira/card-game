package com.letscode.game.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(){
        super("Rodada encerrada! Inicie nova.");
    }
}
