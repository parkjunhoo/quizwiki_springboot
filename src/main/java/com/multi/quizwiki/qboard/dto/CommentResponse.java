package com.multi.quizwiki.qboard.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
@Alias("comment_res")
@Getter
@Setter
public class CommentResponse {
			
		 	private Long comment_id;                       // 댓글 번호 (PK)
		    private Long qboard_id;                   // 게시글 번호 (FK)
		    private String content;                // 내용
		    private String member_id;                 // 작성자
		    private Boolean delete_Yn;              // 삭제 여부
		    private Timestamp regDate;     // 생성일시
		    private Timestamp editDate;    // 최종 수정일시

}
