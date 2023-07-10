package com.multi.quizwiki.manager.service;

import java.util.List;

import com.multi.quizwiki.dto.ProblemDTO;
import com.multi.quizwiki.manager.dto.RecoCategoryDTO;
import com.multi.quizwiki.manager.dto.RecoProblemDTO;
import com.multi.quizwiki.manager.dto.SolvCountDTO;
import com.multi.quizwiki.problem.entity.PrintFileEntity;
import com.multi.quizwiki.problem.entity.ProblemChoiseEntity;

public interface ManagerService {
	
	public List<SolvCountDTO> findSolvCountBySubjectName(String memberId);
	public List<SolvCountDTO> findSolvCountByProblemCateName(String memberId);
	public List<RecoProblemDTO> recommandProblem(String memberId, boolean distinct, int limit);
	
	public List<PrintFileEntity> print_file_findALlByProblemId(int problemId);
	public List<ProblemChoiseEntity> problem_choise_findAllByProblemId(int problemId);
	
	public List<RecoCategoryDTO> bestCate(String memberId);
	public List<RecoCategoryDTO> worstCate(String memberId);
}
