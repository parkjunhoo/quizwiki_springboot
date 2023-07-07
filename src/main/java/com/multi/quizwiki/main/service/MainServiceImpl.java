package com.multi.quizwiki.main.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.quizwiki.dto.PboardDTO;
import com.multi.quizwiki.dto.ProblemDTO;
import com.multi.quizwiki.pboard.dao.PboardDAO;
import com.multi.quizwiki.pboard.entity.PboardEntity;
import com.multi.quizwiki.problem.dao.ProblemDAO;
import com.multi.quizwiki.qboard.dao.QboardDAO;
import com.multi.quizwiki.qboard.entity.QboardEntity;

import lombok.NoArgsConstructor;

@Service
@Transactional
@NoArgsConstructor
public class MainServiceImpl implements MainService{
	
	private PboardDAO pboardDAO;
	private ProblemDAO problemDAO;
	private QboardDAO qboardDAO;
	
	
	
	@Autowired
	public MainServiceImpl(PboardDAO pboardDAO, ProblemDAO problemDAO , QboardDAO qboardDAO) {
		this.pboardDAO = pboardDAO;
		this.problemDAO = problemDAO;
		this.qboardDAO = qboardDAO;
	}
	
	public List<PboardEntity> findTop10ByPboardStatusNotOrderByPboardShowCountDesc() {
		return pboardDAO.findTop10ByPboardStatusNotOrderByPboardShowCountDesc();
	}

	@Override
	public List<PboardDTO> pboard_findOrderByLikeCount(int limit) {
		return pboardDAO.pboard_findOrderByLikeCount(limit);
	}

	@Override
	public List<QboardEntity> findTop10ByDeleteYnNotOrderByViewCountDesc() {
		return qboardDAO.findTop10ByDeleteYnNotOrderByViewCountDesc();
	}

	@Override
	public List<ProblemDTO> problem_findOrderByLike(int limit) {
		return problemDAO.problem_findOrderByLike(limit);
	}
}
