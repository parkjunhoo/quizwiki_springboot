package com.multi.quizwiki.qboard.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
@Alias("commentSearch")
public class CommentSearchDTO extends SearchDto{
	private Long qboard_id; //게시글 번호
}
