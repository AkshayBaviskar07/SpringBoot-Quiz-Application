package com.quiz.app.QuizApplication.service;

import com.quiz.app.QuizApplication.exception.QuestionNotFoundException;
import com.quiz.app.QuizApplication.model.Question;
import com.quiz.app.QuizApplication.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository repo;


    public ResponseEntity<String> addQuestion(Question question){
        repo.save(question);
        return new ResponseEntity<String>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Question>> getAllQuestion() {
        List<Question> questionList = repo.findAll();

        if(!questionList.isEmpty()){
            // return all questions
            return new ResponseEntity<>(questionList, HttpStatus.OK);
        } else{
            // questions not present
            throw new QuestionNotFoundException("Questions not available");
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        List<Question> questions = repo.findByCategory(category);

        if(!questions.isEmpty()){
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } else{
            // question not present
            throw new QuestionNotFoundException("Question not available");
        }
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        Optional<Question> queOptional = repo.findById(question.getId());

        if(queOptional.isPresent()){
            repo.save(question);
            return new ResponseEntity<>("updated", HttpStatus.OK);
        } else{
            // questions not present
            throw new QuestionNotFoundException("Questions not available");
        }
    }

    public ResponseEntity<String> deleteQuestion(Long id) {
        Optional<Question> queOptional = repo.findById(id);

        if(queOptional.isPresent()){
            repo.deleteById(id);
            return new ResponseEntity<>("deleted", HttpStatus.OK);
        } else{
            // questions not present
            throw new QuestionNotFoundException("Questions not available");
        }
    }
}
