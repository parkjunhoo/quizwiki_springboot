package com.multi.quizwiki.qboard.dao;

import java.util.List;

import com.multi.quizwiki.qboard.dto.QboardDTO;
import com.multi.quizwiki.qboard.dto.SearchDto;
public interface QboardDAO {
	public Long save(QboardDTO qboard);
	
	
	 public List<QboardDTO> getBoardList(SearchDto params);
	 
	
	 public QboardDTO getQboardDetail(Long qboard_id);
	 
	 public int update(QboardDTO qboard);
	 
	 
	 public int deleteQboard(Long qboard_id);
	 
	 int count (SearchDto params);
	 
	 void increaseViewCount(Long qboard_id);
	 
	 List<QboardDTO> findByCategory(String category);
	 	
	 
}
