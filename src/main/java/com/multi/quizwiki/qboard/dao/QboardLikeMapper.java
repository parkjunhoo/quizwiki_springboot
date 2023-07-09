package com.multi.quizwiki.qboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.multi.quizwiki.qboard.dto.LikeDTO;
import com.multi.quizwiki.qboard.dto.LikeResponse;

@Mapper
public interface QboardLikeMapper {
	//좋아요 정보 저장
	void addLike(LikeDTO likedto);
	
	void deleteLike(LikeDTO likedto);
	
	 List<LikeResponse> getLike(Long qboard_id);
	
	int isLike(LikeDTO likedto);
	
	
}
