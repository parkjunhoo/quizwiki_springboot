package com.multi.quizwiki.manager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.quizwiki.dto.ProblemDTO;
import com.multi.quizwiki.manager.dao.ManagerDAO;
import com.multi.quizwiki.manager.dto.RecoCategoryDTO;
import com.multi.quizwiki.manager.dto.RecoProblemDTO;
import com.multi.quizwiki.manager.dto.SolvCountDTO;
import com.multi.quizwiki.problem.dao.ProblemDAO;
import com.multi.quizwiki.problem.entity.PrintFileEntity;
import com.multi.quizwiki.problem.entity.ProblemChoiseEntity;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService{
	
	private ManagerDAO dao;
	private ProblemDAO problemDAO;

	@Autowired
	public ManagerServiceImpl(ManagerDAO dao, ProblemDAO problemDAO) {
		this.dao = dao;
		this.problemDAO = problemDAO;
	}
	
	@Override
	public List<SolvCountDTO> findSolvCountBySubjectName(String memberId) {
		return dao.findSolvCountBySubjectName(memberId);
	}

	@Override
	public List<SolvCountDTO> findSolvCountByProblemCateName(String memberId) {
		return dao.findSolvCountByProblemCateName(memberId);
	}

	@Override
	public List<RecoProblemDTO> recommandProblem(String memberId, boolean distinct, int limit) {
		List<RecoProblemDTO> problemList = dao.recommandProblem(memberId, distinct, limit);
		problemList.forEach((p)->{
			List<PrintFileEntity> printFileList = this.print_file_findALlByProblemId(p.getProblemId());
			List<ProblemChoiseEntity> problemChoiseList = this.problem_choise_findAllByProblemId(p.getProblemId());
			p.setProblemFileList(printFileList);
			p.setProblemChoiseList(problemChoiseList);
		});
		return problemList;
	}

	@Override
	public List<PrintFileEntity> print_file_findALlByProblemId(int problemId) {
		return problemDAO.print_file_findALlByProblemId(problemId);
	}

	@Override
	public List<ProblemChoiseEntity> problem_choise_findAllByProblemId(int problemId) {
		return problemDAO.problem_choise_findAllByProblemId(problemId);
	}


	@Override
	public List<RecoCategoryDTO> bestCate(String memberId) {
		return dao.bestCate(memberId);
	}

	@Override
	public List<RecoCategoryDTO> worstCate(String memberId) {
		return dao.worstCate(memberId);
	}

}
