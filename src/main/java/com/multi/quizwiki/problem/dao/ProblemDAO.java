package com.multi.quizwiki.problem.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.multi.quizwiki.dto.ProblemDTO;
import com.multi.quizwiki.problem.entity.PrintFileEntity;
import com.multi.quizwiki.problem.entity.ProblemCateEntity;
import com.multi.quizwiki.problem.entity.ProblemChoiseEntity;
import com.multi.quizwiki.problem.entity.ProblemEntity;
import com.multi.quizwiki.problem.entity.ProblemInquiryEntity;
import com.multi.quizwiki.problem.entity.ProblemLikeEntity;
import com.multi.quizwiki.solv.entity.SolvEntity;

public interface ProblemDAO {
	public ProblemEntity insert(ProblemEntity problem);
	public ProblemChoiseEntity problemChoise_insert(ProblemChoiseEntity problemChoise);
	public void printfile_insert(MultipartFile file, int problemId) throws IllegalStateException, IOException;
	
	public PrintFileEntity printfile_findByPboardId(int problemId);
	public List<ProblemCateEntity> problemCate_findAll();
	
	public ProblemEntity problem_findByProblemId(int problemId);
	public List<ProblemEntity> probleme_findByPboardId(int pboardId);
	
	public void problem_delete(int problemId);
	public void problem_editStatusByPboardId(int pboardId);
	public List<ProblemEntity> problem_findAllByPboardId(int pboardId);
	
	public ProblemLikeEntity problemlike_insert(ProblemLikeEntity problemlike);
	public ProblemLikeEntity problemlike_findByMemberIdAndProblemId(String memberId, int problemId);

	public SolvEntity solv_insert(SolvEntity solv);
	public List<SolvEntity> solv_insertAll(List<SolvEntity> solvList);
	public ProblemInquiryEntity inquiry_insert(ProblemInquiryEntity problemInquiry);
	
	public List<ProblemDTO> problem_findLikedByMemberId(String memberId, int size , int page);
	public List<ProblemDTO> problem_findOrderByInquiry(int limit);
	public List<ProblemDTO> problem_findOrderByLike(int limit);
	
	public List<PrintFileEntity> print_file_findALlByProblemId(int problemId);
	public List<ProblemChoiseEntity> problem_choise_findAllByProblemId(int problemId);
}
