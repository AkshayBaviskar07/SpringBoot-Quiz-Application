package com.quiz.app.QuizApplication.service;

import com.quiz.app.QuizApplication.exception.QuestionNotFoundException;
import com.quiz.app.QuizApplication.model.Question;
import com.quiz.app.QuizApplication.repository.QuestionRepository;
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
class QuestionServiceTest {
    @Autowired
    private QuestionService service;
    @MockBean
    private QuestionRepository repository;
    Question question = null;

    @BeforeEach
    void setUp() {
        question =new Question(
                "Which of the following is a superclass of every class in Java?",
                "Object class",
                "ArrayList class",
                "Abstract class",
                "String",
                "Java",
                "Object class",
                "Easy"
                );
        question.setId(1L);
    }

    @AfterEach
    void tearDown() {
        question=null;
    }

    @Test
    void addQuestion() {
        when(repository.save(question)).thenReturn(question);
        ResponseEntity<String> response = service.addQuestion(question);
        assertEquals("success", response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldGetAllQuestion() {
        when(repository.findAll()).thenReturn(List.of(question));

        ResponseEntity<List<Question>> response = service.getAllQuestion();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(question), response.getBody());
    }

    @Test
    void shouldThrowQuestionNotFoundWhenGetAllQuestion() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        try{
            service.getAllQuestion();
        } catch (QuestionNotFoundException e){
            assertEquals("Questions not available", e.getMessage());
        }

    }

    @Test
    void getQuestionsByCategory() {
        when(repository.findByCategory("Java"))
                .thenReturn(List.of(question));
        ResponseEntity<List<Question>> response = service.getQuestionsByCategory("Java");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(question), response.getBody());
    }

    @Test
    void shouldThrowQuestionNotFoundWhenGetQuestionsByCategory() {
        when(repository.findByCategory("Java")).thenReturn(new ArrayList<>());
        try{
            service.getQuestionsByCategory("Java");
        } catch (QuestionNotFoundException e) {
            assertEquals("Question not available", e.getMessage());
        }
    }

    @Test
    void updateQuestion() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(question));
        ResponseEntity<String> response =
                service.updateQuestion(question);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("updated", response.getBody());
    }

    @Test
    void shouldThrowQuestionNotFoundWhenUpdateQuestion() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        try{
            service.updateQuestion(question);
        } catch(QuestionNotFoundException e) {
            assertEquals("Questions not available", e.getMessage());
        }
    }

    @Test
    void deleteQuestion() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(question));
        ResponseEntity<String> response =
                service.deleteQuestion(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted", response.getBody());
    }

    @Test
    void shouldThrowQuestionNotFoundWhenDeleteQuestion() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        try{
            service.deleteQuestion(1L);
        } catch (QuestionNotFoundException e){
            assertEquals("Questions not available", e.getMessage());
        }
    }
}