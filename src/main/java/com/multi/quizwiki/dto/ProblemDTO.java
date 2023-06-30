package com.multi.quizwiki.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.multi.quizwiki.mypage.dto.ProblemInquiryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("problem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDTO {
	private int problem_id;
	private int problem_cate_id;
	private int pboard_id;
	private String problem_type;
	private String problem_content;
	private int problem_index;
	private String problem_answer;
	private String problem_print;
	private String problem_desc;
	private String problem_status;
	
	private int problemInquiryCount;
	private int problemLikeCount;
}
