package com.multi.quizwiki.problem.service;

import java.util.List;

import com.multi.quizwiki.dto.ProblemDTO;
import com.multi.quizwiki.problem.entity.ProblemEntity;

public interface ProblemService {
	public List<ProblemDTO> problem_findLikedByMemberId(String memberId, int size , int page);
	public List<ProblemDTO> problem_findOrderByInquiry(int limit);
	
	public ProblemEntity problem_findByProblemId(int problemId);
}
