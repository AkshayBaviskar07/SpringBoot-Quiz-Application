package com.quiz.app.QuizApplication.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class QuizLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuizLoggingAspect.class);

    @Before("execution(* com.quiz.app.QuizApplication.controller.QuizController.*(..))")
    public void logControllerBeforeCall(JoinPoint joinPoint){
        LOGGER.info("Controller Before :" + joinPoint.getSignature().getName());
    }

    @After("execution(* com.quiz.app.QuizApplication.controller.QuizController.*(..))")
    public void logControllerAfterCall(JoinPoint joinPoint){
        LOGGER.info("Controller After :" + joinPoint.getSignature().getName());
    }

    @AfterThrowing("execution(* com.quiz.app.QuizApplication.controller.QuizController.*(..))")
    public void logControllerAfterThrowingException(JoinPoint joinPoint){
        LOGGER.info("Controller After Throwing :" + joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* com.quiz.app.QuizApplication.controller.QuizController.*(..))")
    public void logControllerAfterReturning(JoinPoint joinPoint){
        LOGGER.info("Controller After Returning :" + joinPoint.getSignature().getName());
    }

    @Before("execution(* com.quiz.app.QuizApplication.service.QuizService.*(..))")
    public void logMethodBeforeCall(JoinPoint joinPoint){
        LOGGER.info("Method Before :" + joinPoint.getSignature().getName());
    }

    @After("execution(* com.quiz.app.QuizApplication.service.QuizService.*(..))")
    public void logMethodAfterCall(JoinPoint joinPoint){
        LOGGER.info("Method After :" + joinPoint.getSignature().getName());
    }

    @AfterThrowing("execution(* com.quiz.app.QuizApplication.service.QuizService.*(..))")
    public void logMethodAfterThrowingException(JoinPoint joinPoint){
        LOGGER.info("Method After Throwing:" + joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* com.quiz.app.QuizApplication.service.QuizService.*(..))")
    public void logMethodAfterReturning(JoinPoint joinPoint){
        LOGGER.info("Method After Returning :" + joinPoint.getSignature().getName());
    }
}
