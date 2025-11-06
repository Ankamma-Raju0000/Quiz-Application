package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.demo.dao.QuestionDao;
import com.example.demo.model.Question;

@Component
public class QuestionService 
{
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity< List<Question>> getAllQuestions() 
	{
		try {
			return  new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return  new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity< List<Question>> getAllQuestionByCategory(String catgorye) 
	{
		try {
			return new ResponseEntity<>(questionDao.findBycatgorye(catgorye), HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		
	}

	public ResponseEntity< String> addQuestion(Question question) 
	{
		questionDao.save(question);
		return new ResponseEntity<>("SUCCESS",HttpStatus.CREATED);
		
	}
	
	

}
