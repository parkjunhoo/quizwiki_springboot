package com.multi.quizwiki.dto;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("problemsearch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSearchDTO {
	private int problemId;
	private String subjectName;
	private String CateName;
	private Timestamp regDate;
	private int likeCount;
	private String memberId;
	private String content;
	private String type;
	
	///////
	private int startLimit;
	private int endLimit;
}
