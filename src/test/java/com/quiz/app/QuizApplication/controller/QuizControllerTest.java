package com.quiz.app.QuizApplication.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.app.QuizApplication.model.Question;
import com.quiz.app.QuizApplication.model.QuestionWrapper;
import com.quiz.app.QuizApplication.model.Quiz;
import com.quiz.app.QuizApplication.model.Response;
import com.quiz.app.QuizApplication.service.QuizService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuizService quizService;
    @JsonIgnore
    QuestionWrapper question=null;
    @Autowired
    QuizController controller;
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
        quiz=null;
        mockMvc=null;
        quizService=null;
    }

    @Test
    void createQuiz() {
        when(quizService.createQuiz("Java", 1L, "Java Quiz"))
                .thenReturn(new ResponseEntity<>("success", HttpStatus.OK));

        ResponseEntity<String> response = controller
                .createQuiz("Java", 1L, "Java Quiz");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
    }

    @Test
    void getQuizQuestion() throws Exception{
        when(quizService.getQuizById(1))
                .thenReturn(new ResponseEntity<>(List.of(question), HttpStatus.OK));

        this.mockMvc.perform(get("/quiz/get/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void submitQuiz() throws Exception {
        Response response = new Response();
        response.setResponse("Object class");
        List<Response> responses = List.of(response);

        when(quizService.calculateResult(1, responses))
                .thenReturn(new ResponseEntity<>(1, HttpStatus.OK));

        this.mockMvc.perform(post("/quiz/submit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(responses)))
                .andExpect(status().isOk()).andDo(print());
    }


    @Test
    void updateQuiz() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String response = mapper.writeValueAsString(quiz);

        when(quizService.updateQuiz(quiz))
                .thenReturn(new ResponseEntity<>("updated", HttpStatus.OK));
        this.mockMvc.perform(put("/quiz/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(response))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteQuiz() throws Exception {
        when(quizService.deleteQuiz(1))
                .thenReturn(new ResponseEntity<>("delete", HttpStatus.OK));
        this.mockMvc.perform(delete("/quiz/delete/1"))
                .andDo(print()).andExpect(status().isOk());
    }
}