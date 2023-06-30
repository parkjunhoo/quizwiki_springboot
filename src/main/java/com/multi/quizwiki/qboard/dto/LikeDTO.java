package com.multi.quizwiki.qboard.dto;

import lombok.Data;

@Data
public class LikeDTO {
	private	int id;
	private	Long qbaord_id;
	private	String member_id;
	private boolean like_check;

}
