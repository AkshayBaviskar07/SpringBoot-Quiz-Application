package com.quiz.app.QuizApplication.controller;

import com.quiz.app.QuizApplication.model.QuestionWrapper;
import com.quiz.app.QuizApplication.model.Quiz;
import com.quiz.app.QuizApplication.model.Response;
import com.quiz.app.QuizApplication.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,
                                             @RequestParam Long numQ,
                                             @RequestParam String title
    ){
        return quizService.createQuiz(category, numQ, title);
    }

    // find quiz by id
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(@PathVariable Integer id){
        return quizService.getQuizById(id);
    }

    // submit the quiz
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }

    //update quiz
    @PutMapping("update")
    public ResponseEntity<String> updateQuiz(@RequestBody Quiz quiz){
        return quizService.updateQuiz(quiz);
    }

    // delete quiz
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Integer id){
        return quizService.deleteQuiz(id);
    }
}
