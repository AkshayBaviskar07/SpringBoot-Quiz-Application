package com.quiz.app.QuizApplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.app.QuizApplication.model.Question;
import com.quiz.app.QuizApplication.service.QuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService service;
    Question question=null;

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
        mockMvc=null;
        question=null;
        service=null;
    }

    @Test
    void shouldGetAllQuestions() throws Exception{
        when(service.getAllQuestion())
                .thenReturn(new ResponseEntity<>(List.of(question), HttpStatus.OK));

        this.mockMvc.perform(get("/question/allQuestions"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    void shouldAddQuestions() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String response = mapper.writeValueAsString(question);

        when(service.addQuestion(question))
                .thenReturn(new ResponseEntity<>("success", HttpStatus.OK));

        this.mockMvc.perform(post("/question/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(response))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldGetQuestionsByCategory() throws Exception {
        when(service.getQuestionsByCategory("Java"))
                .thenReturn(new ResponseEntity<>(List.of(question), HttpStatus.OK));
        this.mockMvc.perform(get("/question/category/Java"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldUpdateQuestion() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String response = mapper.writeValueAsString(question);

        when(service.updateQuestion(question))
                .thenReturn(new ResponseEntity<>("updated", HttpStatus.OK));
        this.mockMvc.perform(put("/question/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(response))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldDeleteQuestion() throws Exception {
        when(service.deleteQuestion(1L))
                .thenReturn(new ResponseEntity<>("deleted", HttpStatus.OK));
        this.mockMvc.perform(delete("/question/delete/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}