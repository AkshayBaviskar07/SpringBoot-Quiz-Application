package com.quiz.app.QuizApplication.service;

import com.quiz.app.QuizApplication.exception.QuizNotFoundException;
import com.quiz.app.QuizApplication.model.Question;
import com.quiz.app.QuizApplication.model.QuestionWrapper;
import com.quiz.app.QuizApplication.model.Quiz;
import com.quiz.app.QuizApplication.model.Response;
import com.quiz.app.QuizApplication.repository.QuestionRepository;
import com.quiz.app.QuizApplication.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepo;
    @Autowired
    private QuestionRepository queRepo;

    public ResponseEntity<String> createQuiz(String category, Long numQ, String title) {

        List<Question> questions = queRepo.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepo.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(Integer id) {
        Optional<Quiz> quiz = quizRepo.findById(id);
        // if questions are present
        if(quiz.isPresent()){
            // extract them into quiz object
            List<Question> questionsFromDb = quiz.get().getQuestions();

            //convert quiz object into QuestionWrapper object
            List<QuestionWrapper> questionsForUser = new ArrayList<>();

            for(Question question : questionsFromDb){
                QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(),
                                                                      question.getTitle(),
                                                                      question.getAnswer1(),
                                                                      question.getAnswer2(),
                                                                      question.getAnswer3(),
                                                                      question.getAnswer4());

                questionsForUser.add(questionWrapper);
            }

            return new  ResponseEntity<>(questionsForUser, HttpStatus.OK);
        } else{
            throw new QuizNotFoundException("Quiz not found");
        }
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Optional<Quiz> optional = quizRepo.findById(id);

        int i = 0;  // to iterate to the next question
        int right = 0; // to calculate result

        if(optional.isPresent()){
            List<Question> questions = optional.get().getQuestions();
            for(Response response : responses){
                if(response.getResponse().equals(questions.get(i).getRightAnswer())) {
                    right++;
                }
                i++;
            }
            return new ResponseEntity<>(right, HttpStatus.OK);
        } else{
            throw new QuizNotFoundException("Quiz not found");
        }
    }

    public ResponseEntity<String> updateQuiz(Quiz quiz) {
        Optional<Quiz> quizOptional = quizRepo.findById(quiz.getId());

        if(quizOptional.isPresent()){
            quizRepo.save(quiz);
            return new ResponseEntity<>("updated", HttpStatus.OK);
        } else{
            throw new QuizNotFoundException("Quiz not found");
        }
    }

    public ResponseEntity<String> deleteQuiz(Integer id) {
        Optional<Quiz> quizOptional = quizRepo.findById(id);

        if(quizOptional.isPresent()){
            Quiz quiz = quizOptional.get();
            quizRepo.deleteById(id);

            return new ResponseEntity<>("delete", HttpStatus.OK);
        } else{
            throw new QuizNotFoundException("Quiz not found");
        }
    }
}
