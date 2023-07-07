package com.multi.quizwiki.qboard.service;

import java.util.HashMap;
import java.util.Map;

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
	
	public LikeDTO findHeart(Long qboard_id, String member_id) {
		Map<String, Long> id = new HashMap<String,Long>();
		id.put("qboard_id", qboard_id);
		return likemapper.findHeart(qboard_id, member_id);
	}
	

}
