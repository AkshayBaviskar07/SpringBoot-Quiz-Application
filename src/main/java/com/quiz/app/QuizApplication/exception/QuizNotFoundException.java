package com.quiz.app.QuizApplication.exception;

public class QuizNotFoundException extends RuntimeException{

    public QuizNotFoundException(String message){
        super(message);
    }
}
