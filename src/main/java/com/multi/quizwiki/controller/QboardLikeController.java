package com.multi.quizwiki.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multi.quizwiki.qboard.dto.LikeDTO;
import com.multi.quizwiki.qboard.service.LikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class QboardLikeController {
	private final LikeService likeservice;
	
	// 
	@PostMapping("qboard/like/{qboard_id}")
	public LikeDTO qboardLike(@PathVariable Long qboard_id) {
		
		return likeservice.saveLike(qboard_id);

	}
	@DeleteMapping("qboard/like/{qboard_id}")
	public Long deleteLike(Long qboard_id) {
		return likeservice.deleteLike(qboard_id);
		
	}
	

}
