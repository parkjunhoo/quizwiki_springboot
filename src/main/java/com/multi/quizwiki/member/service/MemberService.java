package com.multi.quizwiki.member.service;

import com.multi.quizwiki.dto.MemberDTO;

public interface MemberService {
	
	//로그인
	MemberDTO login(MemberDTO loginUser);
	
	//아이디 중복
	int idCheck(MemberDTO member_id) throws Exception;
	
	//회원가입
	void register(MemberDTO user) throws Exception;
	
	// 아이디 찾기
		public MemberDTO find_id(MemberDTO dto);
	
	//sms
	
	public void certifiedPhoneNumber(String telnum, String numStr);
}
