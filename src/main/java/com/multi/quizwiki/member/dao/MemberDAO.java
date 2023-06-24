package com.multi.quizwiki.member.dao;

import com.multi.quizwiki.dto.MemberDTO;

public interface MemberDAO {
	
	// 로그인
	MemberDTO login(MemberDTO loginUser);
	
	// 아이디 중복 체크
	int idCheck(MemberDTO member_id) throws Exception;
	
	// 비밀번호 체크
	
	void register(MemberDTO user) throws Exception;

	
	// 아이디 찾기
		public MemberDTO find_id(MemberDTO dto);
		
		
		
		public void certifiedPhone(String telnum);
		public void certifiedNum(String numStr);

	
	
}
