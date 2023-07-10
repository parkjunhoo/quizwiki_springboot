package com.multi.quizwiki.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSearchResultDTO {
	private List<Integer> pages;
	private List<ProblemSearchDTO> problemList;
	private int currentPage;
	private int totalPage;
}
