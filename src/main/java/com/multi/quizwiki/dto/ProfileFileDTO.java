package com.multi.quizwiki.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("profile")
public class ProfileFileDTO {
	private String profile_file_id;
	private String profile_file_origin;
	private String profile_file_store;
	private String member_id;
}
