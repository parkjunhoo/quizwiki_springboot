package com.multi.quizwiki.search.dao;

import java.util.List;

import com.multi.quizwiki.dto.ProblemSearchDTO;
import com.multi.quizwiki.dto.ProblemSearchParameterDTO;

public interface SearchDAO {
	public List<ProblemSearchDTO> searchProblem(ProblemSearchParameterDTO param);
	public int searchProblemCount(ProblemSearchParameterDTO param);
}
