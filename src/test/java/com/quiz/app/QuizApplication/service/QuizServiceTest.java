package com.quiz.app.QuizApplication.service;

import com.quiz.app.QuizApplication.exception.QuizNotFoundException;
import com.quiz.app.QuizApplication.model.Question;
import com.quiz.app.QuizApplication.model.QuestionWrapper;
import com.quiz.app.QuizApplication.model.Quiz;
import com.quiz.app.QuizApplication.model.Response;
import com.quiz.app.QuizApplication.repository.QuizRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class QuizServiceTest {
    @Autowired
    private QuizService quizService;
    @MockBean
    private QuizRepository repository;
    QuestionWrapper question=null;
    Quiz quiz=null;

    @BeforeEach
    void setUp() {
        question =new QuestionWrapper(
                1L,
                "Which of the following is a superclass of every class in Java?",
                "Object class",
                "ArrayList class",
                "Abstract class",
                "String"
        );

        quiz=new Quiz("Java Quiz", List.of(new Question(
                "Which of the following is a superclass of every class in Java?",
                "Object class",
                "ArrayList class",
                "Abstract class",
                "String",
                "Java",
                "Object class",
                "Easy"
        )));
        quiz.setId(1);
    }

    @AfterEach
    void tearDown() {
        question=null;
        quizService=null;
        repository=null;
        quiz=null;
    }

    @Test
    void createQuiz() {
        when(repository.save(quiz)).thenReturn(quiz);
        ResponseEntity<String> response = quizService.createQuiz("Java", 1L, "Java Quiz");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("success", response.getBody());
    }

    @Test
    void shouldGetQuizById() {
        when(repository.findById(1)).thenReturn(Optional.of(quiz));
        ResponseEntity<List<QuestionWrapper>> response = quizService.getQuizById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void shouldThrowQuizNotFoundWhenGetQuizById(){
        when(repository.findById(1)).thenReturn(Optional.empty());
        try{
            quizService.getQuizById(1);
        } catch (QuizNotFoundException qe){
            assertEquals("Quiz not found", qe.getMessage());
        }
    }

    @Test
    void calculateResult() {
        when(repository.findById(1)).thenReturn(Optional.of(quiz));
        Response response = new Response();
        response.setResponse("Object class");

        ResponseEntity<Integer> responses = quizService.calculateResult(1, List.of(response));
        assertEquals(HttpStatus.OK, responses.getStatusCode());
        assertEquals(1, responses.getBody());
    }

    @Test
    void shouldThrowQuizNotFoundWhenCalculateResult(){
        when(repository.findById(1)).thenReturn(Optional.empty());
        try{
            quizService.calculateResult(1, new ArrayList<>());
        } catch (QuizNotFoundException qe){
            assertEquals("Quiz not found", qe.getMessage());
        }
    }

    @Test
    void updateQuiz() {
        when(repository.findById(1)).thenReturn(Optional.of(quiz));
        ResponseEntity<String> response = quizService.updateQuiz(quiz);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("updated", response.getBody());
    }

    @Test
    void shouldThrowQuizNotFoundWhenUpdateQuiz(){
        when(repository.findById(1)).thenReturn(Optional.empty());
        try{
            quizService.updateQuiz(new Quiz());
        } catch (QuizNotFoundException qe){
            assertEquals("Quiz not found", qe.getMessage());
        }
    }

    @Test
    void deleteQuiz() {
        when(repository.findById(1)).thenReturn(Optional.of(quiz));
        ResponseEntity<String> response = quizService.deleteQuiz(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("delete", response.getBody());
    }

    @Test
    void shouldThrowQuizNotFoundWhenDeleteQuiz(){
        when(repository.findById(1)).thenReturn(Optional.empty());
        try{
            quizService.deleteQuiz(1);
        } catch (QuizNotFoundException qe){
            assertEquals("Quiz not found", qe.getMessage());
        }
    }
}