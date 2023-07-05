package com.multi.quizwiki.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.multi.quizwiki.qboard.dto.CommentRequest;
import com.multi.quizwiki.qboard.dto.CommentResponse;
import com.multi.quizwiki.qboard.dto.CommentSearchDTO;
import com.multi.quizwiki.qboard.paging.PagingResponse;
import com.multi.quizwiki.qboard.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequiredArgsConstructor
public class QboardCommentController {
	private  final CommentService commentservice;

    // 댓글 상세정보 조회
    @GetMapping("/qboard/{qboard_id}/comments/{comment_id}")
    public CommentResponse findById(@PathVariable final Long qboard_id, @PathVariable final Long comment_id) {
        log.info("댓글 상세정보 조회 컨트롤러 실행");
    	return commentservice.findById(comment_id);
    }
    
	
	
	//댓글 생성
	@PostMapping("/qboard/{qboard_id}/comments")
	public CommentResponse saveCommnet(@PathVariable Long qboard_id, @RequestBody 
			CommentRequest params) {
			log.info("댓글 생성 컨트롤러 실행");
			Long comment_id =commentservice.saveComment(params);
			return commentservice.findById(comment_id);
			}
	//댓글 리스트 조회
	@GetMapping("/qboard/{qboard_id}/comments")
	public PagingResponse<CommentResponse> findAllComment(@PathVariable Long qboard_id, CommentSearchDTO params) {
	
		return commentservice.findAllComment(params);
	}
	//댓글 수정
	@PatchMapping("qboard/{qboard_id}/comments/{comment_id}")
	public CommentResponse updatecomment(@PathVariable Long qboard_id,@PathVariable Long comment_id,
			@RequestBody CommentRequest params ) {
		log.info("댓글 수정 컨트롤러 실행");
		commentservice.updateComment(params);
		log.info("getContent="+params.getContent());
		return commentservice.findById(comment_id);
	}
		
	@DeleteMapping("qboard/{qboard_id}/comments/{comment_id}")
	public Long deletecomment(@PathVariable Long qboard_id, @PathVariable Long comment_id) {
		log.info("댓글 삭제 컨트롤러 실행");
		return commentservice.deleteComment(comment_id);
	}
	
}
