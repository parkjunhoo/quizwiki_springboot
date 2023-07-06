package com.multi.quizwiki.main.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.quizwiki.problem.dao.ProblemDAO;

import lombok.NoArgsConstructor;

@Service
@Transactional
@NoArgsConstructor
public class MainServiceImpl implements MainService{
	
	private ProblemDAO problemDAO;
	
	
	@Autowired
	public MainServiceImpl(ProblemDAO problemDAO) {
		this.problemDAO = problemDAO;
	}
	
	
}
