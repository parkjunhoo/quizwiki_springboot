package com.multi.quizwiki.dto;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Alias("pboard")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PboardDTO {
	private int pboard_id;
	private String member_id; 
	private int pboard_cate_id;
	private String pboard_title; 
	private String pboard_content;
	private Timestamp pboard_reg_date; 
	private Timestamp pboard_edit_date;
	private int pboard_show_count;
	private String pboard_status; 
	
	
	private List<ProblemDTO> plist; //문제리스트 list
	
	private int pboardLikeCount;
	
	

}
