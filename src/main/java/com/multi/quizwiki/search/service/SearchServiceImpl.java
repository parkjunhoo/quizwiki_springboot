package com.multi.quizwiki.search.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.quizwiki.dto.ProblemSearchDTO;
import com.multi.quizwiki.dto.ProblemSearchParameterDTO;
import com.multi.quizwiki.search.dao.SearchDAO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
@Transactional
public class SearchServiceImpl implements SearchService{
	
	private SearchDAO dao;
	
	@Autowired
	public SearchServiceImpl(SearchDAO dao) {
		this.dao = dao;
	}

	@Override
	public List<ProblemSearchDTO> searchProblem(ProblemSearchParameterDTO param) {
		return dao.searchProblem(param);
	}

	@Override
	public int searchProblemCount(ProblemSearchParameterDTO param) {
		return dao.searchProblemCount(param);
	}
}
