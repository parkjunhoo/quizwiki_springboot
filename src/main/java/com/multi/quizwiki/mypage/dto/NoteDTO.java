package com.multi.quizwiki.mypage.dto;



import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("note")
public class NoteDTO {
	private String note_id;
	private String member_id;
	private String note_title;
	private String note_regdate;
	private String note_private;
	private int note_show_count;
	private boolean note_deleted;
}

