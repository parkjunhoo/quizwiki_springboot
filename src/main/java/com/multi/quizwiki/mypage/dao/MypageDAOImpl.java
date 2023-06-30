package com.multi.quizwiki.mypage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.quizwiki.dto.PboardDTO;
import com.multi.quizwiki.mypage.dto.InquryDTO;
import com.multi.quizwiki.mypage.dto.InquryFileDTO;
import com.multi.quizwiki.mypage.dto.InquryReplyDTO;
import com.multi.quizwiki.mypage.dto.NoteDTO;
import com.multi.quizwiki.mypage.dto.PointDTO;
import com.multi.quizwiki.mypage.dto.ProblemInquiryDTO;
import com.multi.quizwiki.qboard.dto.QboardDTO;

@Repository
public class MypageDAOImpl implements MypageDAO{

	SqlSession sqlSessionTemplate;
	
	public MypageDAOImpl() {
		
	}
	@Autowired
	public MypageDAOImpl(SqlSession sqlSessionTemplate) {
		super();
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	// 내포인트 내역 
	@Override
	public List<PointDTO> pointread(String member_id) {
		return sqlSessionTemplate.selectList("com.multi.quizwiki.mypage.pointread",member_id);
	}
	//내포인트 토탈 
	@Override
	public String pointtotal(String member_id) {
		
		return sqlSessionTemplate.selectOne("com.multi.quizwiki.mypage.pointtotal",member_id);
	}
	@Override//포인트 날짜로 조회 
	public List<PointDTO> pointsearch(String startday, String endday,String member_id) {
		Map<String, Object> params = new HashMap<>();
	    params.put("member_id", member_id);
	    params.put("startday", startday);
	    params.put("endday",endday );
		return sqlSessionTemplate.selectList("com.multi.quizwiki.mypage.pointsearch",params);
	}
	@Override//문의사항 리스트
	public List<InquryDTO> inquryread(String member_id, String inqury_category) {
		 Map<String, Object> params = new HashMap<>();
		    params.put("member_id", member_id);
		    params.put("inqury_category", inqury_category);
		    System.out.println(params);
		return sqlSessionTemplate.selectList("com.multi.quizwiki.mypage.inqury",params);
	}
	@Override//문의사항 자세히보기
	public InquryDTO inqurydetail(String inqury_id) {
		
		return sqlSessionTemplate.selectOne("com.multi.quizwiki.mypage.inqurydetail",inqury_id);
	}
	@Override//문의사항 insert
	public int inquryinsert(InquryDTO inqurydto) {
		return sqlSessionTemplate.insert("com.multi.quizwiki.mypage.inquryinsert", inqurydto);
	}
	@Override//문의사항 update
	public int inquryupdate(InquryDTO inqurydto) {
		return sqlSessionTemplate.update("com.multi.quizwiki.mypage.inquryupdate", inqurydto);
	}
	@Override//문의사항 delete
	public int inqurydelete(String inqury_id) {
		return sqlSessionTemplate.delete("com.multi.quizwiki.mypage.inqurydelete", inqury_id);
	}
	@Override//문의사항 답변
	public InquryReplyDTO inquryreply(String inqury_id) {
		return sqlSessionTemplate.selectOne("com.multi.quizwiki.mypage.inquryreply",inqury_id);
	}
	@Override//내문제리스트
	public List<PboardDTO> pboardread(String member_id) {
		return sqlSessionTemplate.selectList("com.multi.quizwiki.mypage.pboardread",member_id);
	}
	@Override//내문제 갯수
	public String pboardcount(String member_id) {
		return sqlSessionTemplate.selectOne("com.multi.quizwiki.mypage.pboardcount",member_id);
	}
	@Override//내문제 날짜로 검색 
	public List<PboardDTO> pboardsearch(String startday, String endday, String member_id) {
		Map<String, Object> params = new HashMap<>();
	    params.put("member_id", member_id);
	    params.put("startday", startday);
	    params.put("endday",endday );
		return sqlSessionTemplate.selectList("com.multi.quizwiki.mypage.pboardsearch",params);
	}
	
	@Override//내질문 리스트 
	public List<QboardDTO> qboardread(String member_id) {
		return sqlSessionTemplate.selectList("com.multi.quizwiki.mypage.qboardread", member_id);
	}
	@Override//오답노트리스트
	public List<NoteDTO> noteread(String member_id) {
		return sqlSessionTemplate.selectList("com.multi.quizwiki.mypage.noteread", member_id);
	}
	@Override//오답노트 갯수 
	public String notecount(String member_id) {
		return sqlSessionTemplate.selectOne("com.multi.quizwiki.mypage.notecount", member_id);
	}
	@Override//날짜로 오답노트 조회하기 
	public List<NoteDTO> notesearch(String startday, String endday, String member_id) {
		Map<String, Object> params = new HashMap<>();
	    params.put("member_id", member_id);
	    params.put("startday", startday);
	    params.put("endday",endday );
		return sqlSessionTemplate.selectList("com.multi.quizwiki.mypage.noteserch",params);
	}
	@Override//오류문항 신고 내역 
	public List<ProblemInquiryDTO> probleminquryread(String member_id) {
		return sqlSessionTemplate.selectList("com.multi.quizwiki.mypage.probleminquiry",member_id);
	}
	@Override//첨부파일 저장 
	public int insertFile(List<InquryFileDTO> inquiryfile) {
		return sqlSessionTemplate.insert("com.multi.quizwiki.mypage.inquryFile", inquiryfile);
	}
	@Override//첨부파일 read
	public List<InquryFileDTO> fileread(String inqury_id) {
		return sqlSessionTemplate.selectList("com.multi.quizwiki.mypage.fileread", inqury_id);
	}
	@Override//첨부파일 삭제
	public int filedelete(String inqury_id) {
		return sqlSessionTemplate.delete("com.multi.quizwiki.mypage.filedelete", inqury_id);
	}
	@Override//첨부파일 다운 
	public InquryFileDTO filedown(InquryFileDTO inquryfile) {
		return sqlSessionTemplate.selectOne("com.multi.quizwiki.mypage.filedown", inquryfile);
	}
	@Override//첨부파일 업데이트 
	public int fileupdate(List<InquryFileDTO> inquiryfile) {
		return sqlSessionTemplate.update("com.multi.quizwiki.mypage.fileupdate", inquiryfile);
	}
	

	
	

}
