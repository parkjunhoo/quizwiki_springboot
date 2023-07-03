package com.multi.quizwiki.mypage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.quizwiki.dto.PboardDTO;
import com.multi.quizwiki.mypage.dao.MypageDAO;
import com.multi.quizwiki.mypage.dto.InquryDTO;
import com.multi.quizwiki.mypage.dto.InquryFileDTO;
import com.multi.quizwiki.mypage.dto.InquryReplyDTO;
import com.multi.quizwiki.mypage.dto.NoteDTO;
import com.multi.quizwiki.mypage.dto.PointDTO;
import com.multi.quizwiki.mypage.dto.ProblemInquiryDTO;
import com.multi.quizwiki.qboard.dto.QboardDTO;

@Service
public class MypageServiceImpl implements MypageService {
	MypageDAO dao;
	
	public MypageServiceImpl() {
		
	}
	@Autowired
	public MypageServiceImpl(MypageDAO dao) {
		super();
		this.dao = dao;
	}

	@Override//내포인트 내역 
	public List<PointDTO> pointread(String member_id) {
		return dao.pointread(member_id);
		
	}
	@Override//내포인트 토탈 
	public String pointtotal(String member_id) {
		return dao.pointtotal(member_id);
	}
	@Override//내포인트 날짜로 조회하기 
	public List<PointDTO> pointsearch(String startday, String endday,String member_id) {
		System.out.println(dao.pointsearch(startday, endday, member_id));
		return dao.pointsearch(startday, endday, member_id);
	}
	
	@Override//문의사항 리스트
	public List<InquryDTO> inquryread(String member_id, String inqury_category) {
		return dao.inquryread(member_id, inqury_category);
	}
	@Override//문의사항 자세히 보기 
	public InquryDTO inqurydetail(String inqury_id) {
		return dao.inqurydetail(inqury_id);
	}
	@Override//문의사항 등록 
	public int inquryinsert(InquryDTO inqurydto) {
		return dao.inquryinsert(inqurydto);
	}
	@Override//첨부파일,내용 insert
	public int inquryinsert(InquryDTO inqurydto, List<InquryFileDTO> inquiryfile) {
		dao.inquryinsert(inqurydto);
		dao.insertFile(inquiryfile);
		return 0;
	}
	@Override
	public int inquryupdate(InquryDTO inqurydto) {
		return dao.inquryupdate(inqurydto);
	}
	@Override//첨부파일 수정 
	public int fileupdate(List<InquryFileDTO> inquiryfile) {
		return dao.fileupdate(inquiryfile);
	}
	
	@Override//문의사항 삭제 
	public int inqurydelete(String inqury_id) {
		return dao.inqurydelete(inqury_id);
	}
	@Override//문의사항 답변 
	public InquryReplyDTO inquryreply(String inqury_id) {
		return dao.inquryreply(inqury_id);
	}
	@Override//오류문항 신고내역 
	public List<ProblemInquiryDTO> probleminquryread(String member_id) {
		return dao.probleminquryread(member_id);
	}
	@Override//내문제 리스트 
	public List<PboardDTO> pboardread(String member_id) {
		System.out.println(dao.pboardread(member_id));
		return dao.pboardread(member_id);
	}
	@Override//내문제 갯수
	public String pboardcount(String member_id) {
		return dao.pboardcount(member_id);
	}
	@Override//내문제 날짜로 조회
	public List<PboardDTO> pboardsearch(String startday, String endday, String member_id) {
		return dao.pboardsearch(startday, endday, member_id);
	}
	@Override//내질문 리스트 
	public List<QboardDTO> qboardread(String member_id) {
		return dao.qboardread(member_id);
	}
	@Override//내오답노트 리스트 
	public List<NoteDTO> noteread(String member_id) {
		return dao.noteread(member_id);
	}
	@Override//내 오답노트 갯수 
	public String notecount(String member_id) {
		return dao.notecount(member_id);
	}
	@Override//오답노트  날짜로 조회 
	public List<NoteDTO> notesearch(String startday, String endday, String member_id) {
		return dao.notesearch(startday, endday, member_id);
	}
	@Override//첨부파일 내용 read
	public List<InquryFileDTO> fileread(String inqury_id) {
		return dao.fileread(inqury_id);
	}
	@Override//첨부파일 삭제
	public int filedelete(String inqury_id) {
		return dao.filedelete(inqury_id);
	}
	@Override//첨부파일 다운 
	public InquryFileDTO filedown(InquryFileDTO inquryfile) {
		return dao.filedown(inquryfile);
	}




	
	
	

	
}
