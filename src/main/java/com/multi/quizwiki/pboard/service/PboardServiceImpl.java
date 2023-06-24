package com.multi.quizwiki.pboard.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.multi.quizwiki.common.FileUploadLogicService;
import com.multi.quizwiki.pboard.dao.PboardDAO;
import com.multi.quizwiki.pboard.entity.PboardCateEntity;
import com.multi.quizwiki.pboard.entity.PboardEntity;
import com.multi.quizwiki.pboard.entity.PboardLikeEntity;
import com.multi.quizwiki.pboard.entity.PboardReplyEntity;
import com.multi.quizwiki.problem.dao.ProblemDAO;
import com.multi.quizwiki.problem.entity.PrintFileEntity;
import com.multi.quizwiki.problem.entity.ProblemCateEntity;
import com.multi.quizwiki.problem.entity.ProblemChoiseEntity;
import com.multi.quizwiki.problem.entity.ProblemEntity;
import com.multi.quizwiki.problem.entity.ProblemInquiryEntity;
import com.multi.quizwiki.problem.entity.ProblemLikeEntity;
import com.multi.quizwiki.solv.entity.SolvEntity;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@Transactional
public class PboardServiceImpl implements PboardService {

	PboardDAO pboardDAO;
	ProblemDAO problemDAO;

	@Autowired
	public PboardServiceImpl(PboardDAO pboardDAO, ProblemDAO problemDAO) {
		this.pboardDAO = pboardDAO;
		this.problemDAO = problemDAO;
	}

	@Override
	public void pboard_edit(PboardEntity pboard, List<ProblemEntity> problemList, List<List<String>> problemChoiseList,
		List<MultipartFile> printfileList, List<Integer> printfileArr) {
		int pboardId = pboard.getPboardId();
		
		pboardDAO.pboard_edit(pboard);
		problemDAO.problem_editStatusByPboardId(pboardId);
		
//		옛날로직. 수정할때 기존 문제들을 삭제하고 새로 추가하는식으로 했었다. 
//		지금은 다른곳에서 수정전의 데이터를 활용해야될 상황이 생겼으므로(오답노트에서 해당 문제를 사용하거나 등.)
//		문제가 수정되더라도 데이터를 DB에서 들고있어야 하므로 status값만 변경하도록 했다.
//		List<ProblemEntity> plist = problemDAO.problem_findAllByPboardId(pboard.getPboardId());
//		for(ProblemEntity p : plist) {
//			problemDAO.problem_delete(p.getProblemId());
//		}
		
		pboard_write_logic(pboard, problemList, problemChoiseList, printfileList, printfileArr);

	}

	@Override
	public PboardEntity pboard_insert(PboardEntity pboard, List<ProblemEntity> problemList,
			List<List<String>> problemChoiseList, List<MultipartFile> printfileList, List<Integer> printfileArr) {
		PboardEntity pi = pboardDAO.insert(pboard);
		pboard_write_logic(pboard, problemList, problemChoiseList, printfileList, printfileArr);
		return pi;
	}

	private void pboard_write_logic(PboardEntity pboard, List<ProblemEntity> problemList,
			List<List<String>> problemChoiseList, List<MultipartFile> printfileList, List<Integer> printfileArr) {
		
		int pboardId = pboard.getPboardId();
		problemList.forEach((p) -> {
			p.setPboardId(pboardId);
			p.setProblemStatus("r");
		});
		
		int i = 0;
		for (ProblemEntity pe : problemList) {
			
			List<String> choiseList = problemChoiseList.get(i);
			
			ProblemEntity currentPe = problemDAO.insert(pe);
			int problemId = currentPe.getProblemId();
			
			if(choiseList != null) {
				choiseList.forEach((c) -> {
					ProblemChoiseEntity pchoise = new ProblemChoiseEntity(problemId,
							Integer.toString(choiseList.indexOf(c)), c);
					
					problemDAO.problemChoise_insert(pchoise);
				});
			}
			
			if(printfileArr.size()>0) {
				int idx = printfileArr.get(0);
				if(idx==i) {
					try{
						 problemDAO.printfile_insert(printfileList.get(0), problemId);
						 printfileList.remove(0);
					 }catch(IllegalStateException | IOException e) {
						 e.printStackTrace(); 
					}
					printfileArr.remove(0);
				}
			}
			
			i++;
		}

	}

	@Override
	public List<PboardCateEntity> pboardCate_findAll() {
		return pboardDAO.pboardCate_findAll();
	}

	@Override
	public List<ProblemCateEntity> problemCate_findAll() {
		return problemDAO.problemCate_findAll();
	}

	@Override
	public Page<PboardEntity> pboard_findByCate(int pboardCateId, org.springframework.data.domain.Pageable pageable) {

		return pboardDAO.pboard_findByCate(pboardCateId, pageable);
	}
	@Override
	public Page<PboardEntity> pboard_findByCateTitleContaining(int pboardCateId, String keyword, Pageable pageable) {
		return pboardDAO.pboard_findByCateTitleContaining(pboardCateId, keyword, pageable);
	}

	@Override
	public Page<PboardEntity> pboard_findByCateMemberIdContaining(int pboardCateId, String keyword, Pageable pageable) {
		return pboardDAO.pboard_findByCateMemberIdContaining(pboardCateId, keyword, pageable);
	}

	@Override
	public PboardEntity pboard_findById(int pboardId) {
		return pboardDAO.pboard_findByPboardId(pboardId);
	}

	@Override
	public void reply_insert(PboardReplyEntity pboardReply) {
		pboardDAO.reply_insert(pboardReply);
	}

	@Override
	public List<ProblemEntity> problem_findByPboardId(int pboardId) {

		return problemDAO.probleme_findByPboardId(pboardId);
	}

	@Override
	public PrintFileEntity printfile_findByProblemId(int problemId) {
		return problemDAO.printfile_findByPboardId(problemId);
	}

	@Override
	public ProblemLikeEntity problemlike_findByMemberIdAndProblemId(String memberId, int problemId) {
		return problemDAO.problemlike_findByMemberIdAndProblemId(memberId, problemId);
	}

	@Override
	public ProblemLikeEntity problemlike_insert(ProblemLikeEntity problemlike) {
		return problemDAO.problemlike_insert(problemlike);
	}

	@Override
	public SolvEntity solv_insert(SolvEntity solv) {
		return problemDAO.solv_insert(solv);
	}

	@Override
	public List<SolvEntity> solv_insertAll(List<SolvEntity> solvList) {
		return problemDAO.solv_insertAll(solvList);
	}

	@Override
	public ProblemInquiryEntity inquiry_insert(ProblemInquiryEntity problemInquiry) {
		return problemDAO.inquiry_insert(problemInquiry);
	}

	@Override
	public PboardLikeEntity pboardlike_insert(PboardLikeEntity pboardLike) {
		return pboardDAO.pboardlike_insert(pboardLike);
	}

	@Override
	public PboardLikeEntity pboardlike_findByMemeberIdAndPboardId(String memberId, int pboardId) {
		return pboardDAO.pboardlike_findByMemeberIdAndPboardId(memberId, pboardId);
	}

	@Override
	public void incrementShowCount(int pboardId) {
		pboardDAO.incrementShowCount(pboardId);
	}



}
