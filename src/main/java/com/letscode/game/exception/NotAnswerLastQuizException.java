package com.letscode.game.exception;

public class NotAnswerLastQuizException extends RuntimeException {
    public NotAnswerLastQuizException(){
        super("Responda última pergunta!");
    }
}
