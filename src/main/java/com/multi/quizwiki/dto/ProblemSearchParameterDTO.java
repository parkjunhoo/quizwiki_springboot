package com.multi.quizwiki.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("problemsearchparam")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSearchParameterDTO {
	private String sort;
	private List<Integer> subjectList;
	private String keyword;
	
	///
	private int startLimit;
	private int sizeLimit;
	
	private int page;
}
