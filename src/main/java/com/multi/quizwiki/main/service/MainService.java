package com.multi.quizwiki.main.service;

import java.util.List;

import com.multi.quizwiki.dto.PboardDTO;
import com.multi.quizwiki.dto.ProblemDTO;
import com.multi.quizwiki.pboard.entity.PboardEntity;
import com.multi.quizwiki.qboard.entity.QboardEntity;

public interface MainService {
	public List<PboardEntity> findTop10ByPboardStatusNotOrderByPboardShowCountDesc();
	public List<PboardDTO> pboard_findOrderByLikeCount(int limit);
	
	public List<QboardEntity> findTop10ByDeleteYnNotOrderByViewCountDesc();
	
	public List<ProblemDTO> problem_findOrderByLike(int limit);
}
