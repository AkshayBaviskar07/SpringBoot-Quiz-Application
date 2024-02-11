package com.quiz.app.QuizApplication.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class QuestionLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionLoggingAspect.class);

    // the called controller will invoke before execution
    @Before("execution(* com.quiz.app.QuizApplication.controller.QuestionController.*(..))")
    public void logControllerCallBeforeExecution(JoinPoint point){
        LOGGER.info("Controller Before :"+point.getSignature().getName());
    }

    @After("execution(* com.quiz.app.QuizApplication.controller.QuestionController.*(..))")
    public void logControllerCallAfterExecution(JoinPoint point){
        LOGGER.info("Controller After :"+point.getSignature().getName());
    }

    @AfterThrowing("execution(* com.quiz.app.QuizApplication.controller.QuestionController.*(..))")
    public void logControllerCallAfterThrowingException(JoinPoint point){
        LOGGER.info("Controller After exception :"+point.getSignature().getName());
    }

    @AfterReturning("execution(* com.quiz.app.QuizApplication.controller.QuestionController.*(..))")
    public void logControllerCallAfterReturning(JoinPoint point){
        LOGGER.info("Controller After returning :"+point.getSignature().getName());
    }

    // the called method will invoke before execution
    @Before("execution(* com.quiz.app.QuizApplication.service.QuestionService.*(..))")
    public void logMethodCallBeforeExecution(JoinPoint point){
        LOGGER.info("Method Before :" + point.getSignature().getName());
    }

    // the called method will invoke after execution
    @After("execution(* com.quiz.app.QuizApplication.service.QuestionService.*(..))")
    public void logMethodCallAfterExecution(JoinPoint point){
        LOGGER.info("After method :" + point.getSignature().getName());
    }

    // the called method will invoke after throwing an exception
    @AfterThrowing("execution(* com.quiz.app.QuizApplication.service.QuestionService.*(..))")
    public void logMethodCallAfterException(JoinPoint point){
        LOGGER.info("After Throwing method :" + point.getSignature().getName());
    }

    // the called method will invoke after successfully executed
    @AfterReturning("execution(* com.quiz.app.QuizApplication.service.QuestionService.*(..))")
    public void logMethodCallAfterSuccessfulReturn(JoinPoint point){
        LOGGER.info("After Returning method :" + point.getSignature().getName());
    }
}
