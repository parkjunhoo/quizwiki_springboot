package com.multi.quizwiki.problem.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.multi.quizwiki.common.FileUploadLogicService;
import com.multi.quizwiki.dto.ProblemDTO;
import com.multi.quizwiki.problem.entity.PrintFileEntity;
import com.multi.quizwiki.problem.entity.ProblemCateEntity;
import com.multi.quizwiki.problem.entity.ProblemChoiseEntity;
import com.multi.quizwiki.problem.entity.ProblemEntity;
import com.multi.quizwiki.problem.entity.ProblemInquiryEntity;
import com.multi.quizwiki.problem.entity.ProblemLikeEntity;
import com.multi.quizwiki.problem.repository.PrintFileRepository;
import com.multi.quizwiki.problem.repository.ProblemCateRepository;
import com.multi.quizwiki.problem.repository.ProblemChoiseRepository;
import com.multi.quizwiki.problem.repository.ProblemInquiryRepository;
import com.multi.quizwiki.problem.repository.ProblemLikeRepository;
import com.multi.quizwiki.problem.repository.ProblemRepository;
import com.multi.quizwiki.solv.entity.SolvEntity;
import com.multi.quizwiki.solv.repository.SolvRepository;

import lombok.NoArgsConstructor;
import util.Utils;

@NoArgsConstructor
@Repository
public class ProblemDAOImpl implements ProblemDAO {

	ProblemRepository problemRepo;
	ProblemCateRepository problemCateRepo;
	ProblemChoiseRepository problemChoiseRepo;
	PrintFileRepository printFileRepo;
	ProblemLikeRepository problemLikeRepo;
	SolvRepository solvRepo;
	ProblemInquiryRepository problemInquiryRepo;
	FileUploadLogicService fileUpload;
	
	SqlSession ss;
	
	@Autowired
	public ProblemDAOImpl(
			ProblemRepository problemRepo, 
			ProblemChoiseRepository problemChoiseRepo, 
			ProblemCateRepository problemCateRepo,
			PrintFileRepository printFileRepo,
			FileUploadLogicService fileUpload,
			ProblemLikeRepository problemLikeRepo,
			SolvRepository solvRepo,
			ProblemInquiryRepository problemInquiryRepo,
			SqlSession ss) {
		
		this.problemRepo = problemRepo;
		this.problemChoiseRepo = problemChoiseRepo;
		this.problemCateRepo = problemCateRepo;
		this.printFileRepo = printFileRepo;
		this.fileUpload = fileUpload;
		this.problemLikeRepo = problemLikeRepo;
		this.solvRepo = solvRepo;
		this.problemInquiryRepo = problemInquiryRepo;
		this.ss = ss;
	}
	
	@Override
	public ProblemEntity insert(ProblemEntity problem) {
		return problemRepo.save(problem);
	}

	@Override
	public List<ProblemCateEntity> problemCate_findAll() {
		return problemCateRepo.findAll();
	}

	@Override
	public void printfile_insert(MultipartFile file, int problemId) throws IllegalStateException, IOException {
		String store = fileUpload.uploadFile(file,"printfile");
		PrintFileEntity pf = new PrintFileEntity(problemId, file.getOriginalFilename(), store);
		printFileRepo.save(pf);
	}

	@Override
	public ProblemChoiseEntity problemChoise_insert(ProblemChoiseEntity problemChoise) {
		return problemChoiseRepo.save(problemChoise);
	}

	@Override
	public List<ProblemEntity> probleme_findByPboardId(int pboardId) {
		return problemRepo.findAllByPboardId(pboardId);
	}

	@Override
	public PrintFileEntity printfile_findByPboardId(int problemId) {
		return printFileRepo.findByProblemId(problemId);
	}

	@Override
	public void problem_delete(int problemId) {
		printFileRepo.deleteAllByProblemId(problemId);
		problemChoiseRepo.deleteAllByProblemId(problemId);
		problemRepo.flush();
		ProblemEntity p = problemRepo.findById(problemId).get();
		problemRepo.delete(p);
	}
	
	@Override
	public void problem_editStatusByPboardId(int pboardId) {
		List<ProblemEntity> problem = problemRepo.findAllByPboardIdAndProblemStatusNot(pboardId,"p");
		problem.forEach((p)->{
			p.setProblemStatus("p");
		});
	}

	@Override
	public List<ProblemEntity> problem_findAllByPboardId(int pboardId) {
		return problemRepo.findAllByPboardId(pboardId);
	}

	@Override
	public ProblemLikeEntity problemlike_findByMemberIdAndProblemId(String memberId, int problemId) {
		return problemLikeRepo.findByMemberIdAndProblemId(memberId, problemId);
	}

	@Override
	public ProblemLikeEntity problemlike_insert(ProblemLikeEntity problemlike) {
		return problemLikeRepo.save(problemlike);
	}

	@Override
	public SolvEntity solv_insert(SolvEntity solv) {
		return solvRepo.save(solv);
	}

	@Override
	public List<SolvEntity> solv_insertAll(List<SolvEntity> solvList) {
		return solvRepo.saveAll(solvList);
	}

	@Override
	public List<ProblemDTO> problem_findLikedByMemberId(String memberId, int size, int page) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("memberId", memberId);
		Utils.addPageParam(paramMap, size, page);
		return ss.selectList("com.multi.quizwiki.problem.selectLikedProblemByMemberId",paramMap);
	}

	@Override
	public ProblemInquiryEntity inquiry_insert(ProblemInquiryEntity problemInquiry) {
		return problemInquiryRepo.save(problemInquiry);
	}

	@Override
	public List<ProblemDTO> problem_findOrderByInquiry(int limit) {
		return ss.selectList("com.multi.quizwiki.problem.selectProblemOrderByInquiry",limit);
	}


}
