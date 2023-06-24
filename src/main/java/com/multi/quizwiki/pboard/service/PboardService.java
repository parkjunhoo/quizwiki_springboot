package com.multi.quizwiki.pboard.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.multi.quizwiki.pboard.entity.PboardCateEntity;
import com.multi.quizwiki.pboard.entity.PboardEntity;
import com.multi.quizwiki.pboard.entity.PboardLikeEntity;
import com.multi.quizwiki.pboard.entity.PboardReplyEntity;
import com.multi.quizwiki.problem.entity.PrintFileEntity;
import com.multi.quizwiki.problem.entity.ProblemCateEntity;
import com.multi.quizwiki.problem.entity.ProblemEntity;
import com.multi.quizwiki.problem.entity.ProblemInquiryEntity;
import com.multi.quizwiki.problem.entity.ProblemLikeEntity;
import com.multi.quizwiki.solv.entity.SolvEntity;

public interface PboardService {
	
	public PboardEntity pboard_insert(
			PboardEntity pboard,
			List<ProblemEntity> problemList,
			List<List<String>> problemChoiseList,
			List<MultipartFile> printfileList,
			List<Integer> printfileArr);
	
	public void reply_insert(PboardReplyEntity pboardReply);
	
	public void pboard_edit(
			PboardEntity pboard,
			List<ProblemEntity> problemList,
			List<List<String>> problemChoiseList,
			List<MultipartFile> printfileList,
			List<Integer> printfileArr);
	

	public Page<PboardEntity> pboard_findByCate(int pboardCateId , Pageable pageable);
	public Page<PboardEntity> pboard_findByCateTitleContaining(int pboardCateId,String keyword , Pageable pageable);
	public Page<PboardEntity> pboard_findByCateMemberIdContaining(int pboardCateId, String keyword , Pageable pageable);
	public PboardEntity pboard_findById(int pboardId);
	
	public PrintFileEntity printfile_findByProblemId(int problemId);
	
	public List<PboardCateEntity> pboardCate_findAll();
	public List<ProblemEntity> problem_findByPboardId(int pboardId);
	public List<ProblemCateEntity> problemCate_findAll();
	
	public ProblemLikeEntity problemlike_findByMemberIdAndProblemId(String memberId, int problemId);
	public ProblemLikeEntity problemlike_insert(ProblemLikeEntity problemlike);
	
	public SolvEntity solv_insert(SolvEntity solv);
	public List<SolvEntity> solv_insertAll(List<SolvEntity> solvList);
	
	public ProblemInquiryEntity inquiry_insert(ProblemInquiryEntity problemInquiry);
	
	public PboardLikeEntity pboardlike_insert(PboardLikeEntity pboardLike);
	
	public PboardLikeEntity pboardlike_findByMemeberIdAndPboardId(String memberId, int pboardId);
	
	public void incrementShowCount(int pboardId);
}
