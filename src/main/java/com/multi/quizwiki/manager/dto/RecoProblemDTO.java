package com.multi.quizwiki.manager.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.multi.quizwiki.problem.entity.PrintFileEntity;
import com.multi.quizwiki.problem.entity.ProblemChoiseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("recoproblem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecoProblemDTO {
	private int problemId;
	private int problemCateId;
	private String problemCateName;
	private int pboardId;
	private String problemType;
	private String problemContent;
	private int problemIndex;
	private String problemAnswer;
	private String problemPrint;
	private String problemDesc;
	private String problemStatus;
	
	
	private List<PrintFileEntity> problemFileList;
	private List<ProblemChoiseEntity> problemChoiseList;
	
}
