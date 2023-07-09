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
	private Long id;
	private	Long qboard_id;
	private	String member_id;
	
	public LikeDTO(Long qboard_id , String member_id) {
		this.qboard_id = qboard_id;
		this.member_id = member_id;
	}
}
