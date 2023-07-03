package com.multi.quizwiki.qboard.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Alias("like")
@NoArgsConstructor
@AllArgsConstructor	
public class LikeDTO {
	private	int id;
	private	Long qbaord_id;
	private	String member_id;
	private boolean like_check;

}
