package com.multi.quizwiki.manager.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("solvCount")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolvCountDTO {
	
	private String subjectName;
	private int subjectId;
	private int wrongCount;
	private int correctCount;
	private int totalCount;
	
}
