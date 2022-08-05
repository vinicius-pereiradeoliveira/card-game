package com.letscode.game.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LimitExceededPlayException.class)
    public ResponseEntity<Object> handleLimitExceededException(
            LimitExceededPlayException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message","Rodada encerrada! Inicie nova.");

        return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(LimitErrorsExceededException.class)
    public ResponseEntity<Object> handleLimitErrorsException(
            LimitErrorsExceededException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message","Que pena, você errou mais de 3. Inicie nova rodada!");

        return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NotAnswerLastQuizException.class)
    public ResponseEntity<Object> handleNotAnswerLastQuizException(
            NotAnswerLastQuizException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message","Responda última pergunta!");

        return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<Object> handleNotAnswerLastQuizException(
            LoginFailedException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message","Necessário Login para Jogar!");

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

}

