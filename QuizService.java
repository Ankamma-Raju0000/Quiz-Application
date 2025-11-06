package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.demo.dao.QuestionDao;
import com.example.demo.dao.QuizDao;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionWrapper;
import com.example.demo.model.Quiz;

@Component
public class QuizService 
{
	@Autowired
	QuizDao quizdao;
	
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title)
	{
		List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
		
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizdao.save(quiz);
		
		return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) 
	{
		Optional<Quiz> quiz= quizdao.findById(id);
		List<Question> questionFromDb=quiz.get().getQuestions();
		List<QuestionWrapper> questionForUser=new ArrayList<QuestionWrapper>();
		for(Question q : questionFromDb)
		{
			QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestiontitle(),q.getOption1(),
					q.getOption2(),q.getOption3(),q.getOption4());
			questionForUser.add(qw);
		}
		
		
		return new ResponseEntity<>(questionForUser, HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> response) 
	{
		Quiz quiz=quizdao.findById(id).get();
		List<Question> questions= quiz.getQuestions();
		int right=0;
		int i=0;
		for (Response response2 : response) 
		{
			if(response2.getResponse().equals(questions.get(i).getRightAnswer()))
				right++;
			i++;
			
		}
		
		return new ResponseEntity<>(right,HttpStatus.OK);
	}

}
