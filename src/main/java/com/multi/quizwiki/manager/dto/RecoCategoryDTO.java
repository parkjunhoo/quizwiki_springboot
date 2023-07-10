package com.multi.quizwiki.manager.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("recocate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecoCategoryDTO {
	private String cateName;
	private String totalCount;
	private double rate;
}
