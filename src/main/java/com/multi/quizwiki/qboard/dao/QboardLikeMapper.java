package com.multi.quizwiki.qboard.dao;

import org.apache.ibatis.annotations.Mapper;

import com.multi.quizwiki.qboard.dto.LikeDTO;

@Mapper
public interface QboardLikeMapper {
	//좋아요 정보 저장
	void saveLike(Long qboard_id);
	
	void delelteLike(Long qboard_id);
	
	LikeDTO findHeart(Long qobard_id, String member_id);
	
	
}
