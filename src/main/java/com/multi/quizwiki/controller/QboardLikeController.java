package com.multi.quizwiki.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.qboard.dto.LikeDTO;
import com.multi.quizwiki.qboard.service.LikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequiredArgsConstructor
public class QboardLikeController {
	private final LikeService likeservice;
	/*
	 * //좋아요 여부 확인
	 * 
	 * @GetMapping("qboard/{qboard_id}/like/{member_id}") public LikeDTO
	 * checkLike(@PathVariable Long qboard_id, @PathVariable String member_id) {
	 * log.info("좋아요 컨트롤러 실행"); return likeservice.findHeart(qboard_id);
	 * 
	 * } //좋아요 저장
	 * 
	 * @PostMapping("qboard/{qboard_id}/like") public Long qboardLike(@PathVariable
	 * Long qboard_id) {
	 * 
	 * return likeservice.;
	 * 
	 * } //좋아요 삭제
	 * 
	 * @DeleteMapping("qboard/{qboard_id}/like/{member_id}") public Long
	 * deleteLike(@PathVariable Long qboard_id,@PathVariable String memeber_id ) {
	 * return likeservice.deleteLike(qboard_id, member_id);;
	 * 
	 * }
	 */
	
	@GetMapping("/like/add")
	public int addLike(@PathVariable(value ="qboard_id") Long qboard_id, Model model
			                  ,MemberDTO member) {
		likeservice.addLike(qboard_id, member.getMember_id());
		
		return likeservice.count(qboard_id);
	}
	
	@GetMapping("/like/delete")
	public int deletelike(@PathVariable(value="qboard_id") Long qboard_id,MemberDTO member) {
		likeservice.deleteLike(qboard_id, member.getMember_id());
		return likeservice.count(qboard_id);
	}

}
