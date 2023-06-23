package com.multi.quizwiki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.multi.quizwiki.qboard.dto.CommentRequest;
import com.multi.quizwiki.qboard.dto.CommentResponse;
import com.multi.quizwiki.qboard.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequiredArgsConstructor
public class QboardCommentController {
	private CommentService commentservice;
	@Autowired
	public QboardCommentController(CommentService commentservice) {
		super();
		this.commentservice = commentservice;
	}
	
	
	//댓글 생성
	@PostMapping("/qboard/{qboard_id}/comments")
	public CommentResponse saveCommnet(@PathVariable Long qboard_id, @RequestBody 
			CommentRequest params) {
			log.info("댓글 생성 컨트롤러 실행");
			Long comment_id =commentservice.saveComment(params);
			return commentservice.findCommentById(comment_id);
			}
	//댓글 리스트 조회
	@GetMapping("/qboard/{qboard_id}/comments")
	public List<CommentResponse> findAllComment(@PathVariable Long qboard_id) {
		return commentservice.findAllComment(qboard_id);
	}
	
	@PatchMapping("qboard/{qboard_id}/comments")
	public CommentResponse updatecomment(@PathVariable Long qboard_id,@PathVariable Long comment_id,
			@RequestBody CommentRequest params ) {
		commentservice.updateComment(params);
		return commentservice.findCommentById(comment_id);
	}
	
	@DeleteMapping("qboard/{qboard_id}/comments")
	public Long deletecomment(@PathVariable Long qboard_id, @PathVariable Long comment_id) {
		return commentservice.deleteComment(comment_id);
	}
	
}
