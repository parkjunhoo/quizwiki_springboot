package com.multi.quizwiki.manager.dao;

import java.util.List;

import com.multi.quizwiki.manager.dto.RecoProblemDTO;
import com.multi.quizwiki.manager.dto.SolvCountDTO;

public interface ManagerDAO {
	
	public List<SolvCountDTO> findSolvCountBySubjectName(String memberId);
	public List<SolvCountDTO> findSolvCountByProblemCateName(String memberId);
	public List<RecoProblemDTO> recommandProblem(String memberId, boolean distinct, int limit);
}
