package com.multi.quizwiki.dao;

import java.util.List;

import com.multi.quizwiki.dto.InquryDTO;
import com.multi.quizwiki.dto.InquryReplyDTO;
import com.multi.quizwiki.dto.NoteDTO;
import com.multi.quizwiki.dto.PboardDTO;
import com.multi.quizwiki.dto.PointDTO;
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
	
	//내문제보기
	List<PboardDTO> pboardread(String member_id);
	//내문제갯수
	String pboardcount(String member_id);
	
	//내질문 리스트
	List<QboardDTO> qboardread(String member_id);
	
	//내오답노트보기
	List<NoteDTO> noteread(String member_id);
	//내오답노트갯수
	String notecount(String member_id);
}
