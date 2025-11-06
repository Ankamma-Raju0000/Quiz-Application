package com.example.demo.dao;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer>
{
	List<Question> findBycatgorye(String catgorye);

	@Query(value = "SELECT * FROM question q\r\n"
			+ "WHERE q.catgorye = :category\r\n"
			+ "ORDER BY RAND()\r\n"
			+ "LIMIT :numQ;",nativeQuery = true)
	List<Question> findRandomQuestionsByCategory(String category, int numQ);
}
