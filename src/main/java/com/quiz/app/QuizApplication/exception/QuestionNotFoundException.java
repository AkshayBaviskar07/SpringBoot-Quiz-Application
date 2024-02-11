package com.quiz.app.QuizApplication.exception;

public class QuestionNotFoundException extends RuntimeException{

    public QuestionNotFoundException(String message){
        super(message);
    }
}
