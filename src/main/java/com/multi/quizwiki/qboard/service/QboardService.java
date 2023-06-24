package com.multi.quizwiki.qboard.service;

import com.multi.quizwiki.qboard.dto.QboardDTO;
import com.multi.quizwiki.qboard.dto.SearchDto;
import com.multi.quizwiki.qboard.paging.PagingResponse;
public interface QboardService {
	 public Long save(QboardDTO qboard);
	 

	public PagingResponse<QboardDTO> getBoardList(SearchDto params);	
	
	 public QboardDTO getQboardDetail(Long qboard_id);
	 		
	 
	 public int update(QboardDTO qboard);
	 
	 public int deleteQboard(Long qboard_id);

	  int count(SearchDto params);
} 