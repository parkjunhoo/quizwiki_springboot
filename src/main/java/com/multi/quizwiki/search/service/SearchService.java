package com.multi.quizwiki.search.service;

import java.util.List;

import com.multi.quizwiki.dto.ProblemSearchDTO;
import com.multi.quizwiki.dto.ProblemSearchParameterDTO;

public interface SearchService {

	public List<ProblemSearchDTO> searchProblem(ProblemSearchParameterDTO param);
	public int searchProblemCount(ProblemSearchParameterDTO param);
}
