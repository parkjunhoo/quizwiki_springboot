package com.multi.quizwiki.qboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.multi.quizwiki.qboard.dto.CommentRequest;
import com.multi.quizwiki.qboard.dto.CommentResponse;
import com.multi.quizwiki.qboard.dto.CommentSearchDTO;

@Mapper
public interface CommentMapper {
		//댓글저장
	void save(CommentRequest params);
	
	//댓글 상세정보 조회
	CommentResponse findById(Long comment_id);
	
	void update(CommentRequest params);
	
	void deleteById(Long comment_id);
	
	List<CommentResponse> findAll(CommentSearchDTO params);
	
	int count(CommentSearchDTO params);
	
	
}
