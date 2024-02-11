package com.quiz.app.QuizApplication.controller;

import com.quiz.app.QuizApplication.model.Question;
import com.quiz.app.QuizApplication.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private QuestionService service;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> allQuestions(){
        return service.getAllQuestion();
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestions(@RequestBody Question question){
        return new ResponseEntity<String>(String.valueOf(service.addQuestion(question)), HttpStatus.CREATED);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return service.getQuestionsByCategory(category);
    }
    @PutMapping("update")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question){
        return service.updateQuestion(question);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id){
        return service.deleteQuestion(id);
    }
}
