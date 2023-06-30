package com.multi.quizwiki.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class solvSearchParam {
	private String problemCateId;
	private Boolean solvRight;
	private String startDate;
	private String endDate;
}
