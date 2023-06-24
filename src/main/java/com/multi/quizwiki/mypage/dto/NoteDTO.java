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
@Alias("note")
public class NoteDTO {
	private String note_id;
	private String solv_id;
	private String member_id;
	private String solv_title;
	private String solv_content;
	private Date solv_reg_date;
	private String solv_open;
}

