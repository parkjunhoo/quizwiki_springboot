package com.multi.quizwiki.mypage.dto;



import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("inquryReply")
public class InquryReplyDTO {
	private String inqury_reply_id;
	private String inqury_id;
	private String inqury_reply_content;
	private String regdate;
	
}

