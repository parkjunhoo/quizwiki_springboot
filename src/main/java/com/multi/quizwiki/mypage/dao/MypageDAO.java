package com.multi.quizwiki.mypage.dao;

import java.util.List;

import com.multi.quizwiki.dto.PboardDTO;
import com.multi.quizwiki.mypage.dto.InquryDTO;
import com.multi.quizwiki.mypage.dto.InquryFileDTO;
import com.multi.quizwiki.mypage.dto.InquryReplyDTO;
import com.multi.quizwiki.mypage.dto.NoteDTO;
import com.multi.quizwiki.mypage.dto.PointDTO;
import com.multi.quizwiki.mypage.dto.ProblemInquiryDTO;
import com.multi.quizwiki.qboard.dto.QboardDTO;

public interface MypageDAO {
	//내포인트 내역 
	List<PointDTO> pointread(String member_id);
	//내 포인트 토탈점수 
	String pointtotal(String member_id);
	//포인트 날짜로 조회하기 
	List<PointDTO> pointsearch(String startday,String endday,String member_id);
	
	//문의사항 내역 보기
	List<InquryDTO> inquryread(String member_id,String inqury_category);
	//문의사항 자세히보기
	InquryDTO inqurydetail(String inqury_id);
	//문의사항 입력 
	int inquryinsert(InquryDTO inqurydto);
	//문의사항 수정 
	int inquryupdate(InquryDTO inqurydto);
	//문의사항 삭제 
	int inqurydelete(String inqury_id);
	//문의사항 답변
	InquryReplyDTO inquryreply(String inqury_id);
	
	//오류문항 신고내역 
	List<ProblemInquiryDTO> probleminquryread(String member_id);
	
	//내문제보기
	List<PboardDTO> pboardread(String member_id);
	//내문제갯수
	String pboardcount(String member_id);
	//내문제 날짜로 조회하기 
	List<PboardDTO> pboardsearch(String startday,String endday,String member_id);
	
	//내질문 리스트
	List<QboardDTO> qboardread(String member_id);
	
	//내오답노트보기
	List<NoteDTO> noteread(String member_id);
	//내오답노트갯수
	String notecount(String member_id);
	//날짜로 조회하기 
	List<NoteDTO> notesearch(String startday,String endday,String member_id);
	
	//첨부파일 저장 
	int insertFile(List<InquryFileDTO> inquiryfile);
	int fileupdate(List<InquryFileDTO> inquiryfile);
	//첨부파일read
	List<InquryFileDTO> fileread (String inqury_id);
	//첨부파일 삭제
	int filedelete(String inqury_id);
	//첨부파일 다운로드
	InquryFileDTO filedown(InquryFileDTO inquryfile);
}
