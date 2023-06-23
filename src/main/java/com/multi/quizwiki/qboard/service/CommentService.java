package com.multi.quizwiki.qboard.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.maven.doxia.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.quizwiki.qboard.dao.CommentMapper;
import com.multi.quizwiki.qboard.dto.CommentRequest;
import com.multi.quizwiki.qboard.dto.CommentResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
	@Autowired
	private CommentMapper commentmapper;
	
	
	@Transactional
	public Long saveComment(CommentRequest params) {
		log.info("댓글 서비스 실행");
		commentmapper.save(params);
		log.info("댓글 서비스"+params.getComment_id());
		return params.getComment_id();
	}
	
	 public CommentResponse findCommentById(final Long comment_id) {
	        return commentmapper.findById(comment_id);
	    }
	
	 	@Transactional
	    public Long updateComment(final CommentRequest params) {
	        commentmapper.update(params);
	        return params.getComment_id();
	    }

	    /**
	     * 댓글 삭제
	     * @param id - PK
	     * @return PK
	     */
	    @Transactional
	    public Long deleteComment(final Long comment_id) {
	        commentmapper.deleteById(comment_id);
	        return comment_id;
	    }

	    /**
	     * 댓글 리스트 조회
	     * @param postId - 게시글 번호 (FK)
	     * @return 특정 게시글에 등록된 댓글 리스트
	     */
	    public List<CommentResponse> findAllComment(final Long qboard_id) {
	        return commentmapper.findAll(qboard_id);
	    }
}
