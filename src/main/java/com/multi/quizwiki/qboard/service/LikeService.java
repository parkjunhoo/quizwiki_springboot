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
	
	public LikeDTO saveLike(Long qboard_id) {
		 likemapper.saveLike(qboard_id);
		return null;
	}
	
	public Long deleteLike(Long qboard_id) {
		likemapper.delelteLike(qboard_id);
		return qboard_id;
	}
	
	
	

}
