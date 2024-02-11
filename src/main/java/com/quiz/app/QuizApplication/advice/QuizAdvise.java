package com.quiz.app.QuizApplication.advice;

import com.quiz.app.QuizApplication.exception.QuestionNotFoundException;
import com.quiz.app.QuizApplication.exception.QuizNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QuizAdvise {

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<QuestionNotFoundException> questionNotFound(QuestionNotFoundException exception){
        // will trigger when question not found
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<QuizNotFoundException> quizNotFound(QuizNotFoundException exception){
        // will trigger when quiz not found
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> someException(Exception exception){
        // will trigger some exception
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
