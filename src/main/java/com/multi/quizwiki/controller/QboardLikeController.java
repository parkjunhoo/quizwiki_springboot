package com.multi.quizwiki.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multi.quizwiki.qboard.dto.LikeDTO;
import com.multi.quizwiki.qboard.service.LikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class QboardLikeController {
	private final LikeService likeservice;
	//좋아요 여부 확인
	@GetMapping("qboard/{qboard_id}/like/{member_id}")
	public LikeDTO checkLike(@PathVariable Long qboard_id, @PathVariable String member_id) {
		return likeservice.likeCheck(qboard_id, member_id);

	}
	//좋아요 저장 
	@PostMapping("qboard/{qboard_id}/like")
	public Long qboardLike(@PathVariable Long qboard_id) {
		
		return likeservice.saveLike(qboard_id);

	}
	//좋아요 삭제
	@DeleteMapping("qboard/{qboard_id}/like/{member_id}")
	public Long deleteLike(@PathVariable Long qboard_id,@PathVariable String memeber_id ) {
		return likeservice.deleteLike(qboard_id);
		
	}
	

}
