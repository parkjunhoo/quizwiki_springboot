package com.multi.quizwiki.qboard.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.multi.quizwiki.qboard.dao.QboardLikeMapper;
import com.multi.quizwiki.qboard.dto.LikeDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
	
	private final QboardLikeMapper likemapper;
	
	public void addLike(Long qboard_id, String member_id) {
		 LikeDTO likedto = new LikeDTO(qboard_id,member_id);
		likemapper.addLike(likedto);
		 
	}
	
	
	public void deleteLike(Long qboard_id, String member_id	) {
		
		LikeDTO likedto = new LikeDTO(qboard_id, member_id); 
		likemapper.deleteLike(likedto);
	}
	
	public boolean isLike(Long qboard_id, String member_id) {
		LikeDTO likedto = new LikeDTO(qboard_id, member_id); 
		int res =likemapper.isLike(likedto);
		return res == 0? false:true; 
	}
	//좋아요 개수
	public int count(Long qboard_id) {
		return likemapper.getLike(qboard_id).size();
	}
	

}
