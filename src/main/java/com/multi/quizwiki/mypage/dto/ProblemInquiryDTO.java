package com.multi.quizwiki.mypage.dto;



import java.sql.Date;

import org.apache.ibatis.type.Alias;

import com.multi.quizwiki.dto.ProblemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("probleminquiry")
public class ProblemInquiryDTO {
	private String problem_inquiry_id;
	private String problem_id;
	private String member_id;
	private String problem_inquiry_content;

}

