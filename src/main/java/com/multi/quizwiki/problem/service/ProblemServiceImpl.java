package com.multi.quizwiki.problem.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.quizwiki.dto.ProblemDTO;
import com.multi.quizwiki.problem.dao.ProblemDAO;
import com.multi.quizwiki.problem.entity.ProblemCateEntity;
import com.multi.quizwiki.problem.entity.ProblemEntity;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@Transactional
public class ProblemServiceImpl implements ProblemService{
	
	private ProblemDAO dao;
	
	@Autowired
	public ProblemServiceImpl(ProblemDAO dao) {
		this.dao = dao;
	}

	@Override
	public List<ProblemDTO> problem_findLikedByMemberId(String memberId, int size, int page) {
		return dao.problem_findLikedByMemberId(memberId, size, page);
	}

	@Override
	public List<ProblemDTO> problem_findOrderByInquiry(int limit) {
		return dao.problem_findOrderByInquiry(limit);
	}

	@Override
	public ProblemEntity problem_findByProblemId(int problemId) {
		return dao.problem_findByProblemId(problemId);
	}

	@Override
	public List<ProblemCateEntity> problemCate_findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
