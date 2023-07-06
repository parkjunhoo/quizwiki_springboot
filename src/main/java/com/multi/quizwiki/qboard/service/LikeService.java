package com.multi.quizwiki.qboard.service;

import org.springframework.stereotype.Service;

import com.multi.quizwiki.qboard.dao.QboardLikeMapper;
import com.multi.quizwiki.qboard.dto.LikeDTO;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class LikeService {
	
	private final QboardLikeMapper likemapper;
	
	public Long saveLike(Long qboard_id) {
		 likemapper.saveLike(qboard_id);
		 return qboard_id;
	}
	
	public Long deleteLike(Long qboard_id) {
		likemapper.delelteLike(qboard_id);
		return qboard_id;
	}
	
	public LikeDTO likeCheck(Long qboard_id, String member_id) {
		
		return likemapper.likeCheck(qboard_id, member_id);
	}
	

}
