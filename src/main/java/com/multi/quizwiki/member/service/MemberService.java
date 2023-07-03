
package com.multi.quizwiki.member.service;

import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.member.entity.MemberEntity;

public interface MemberService {

	// 로그인
	MemberDTO login(MemberDTO loginUser);

	// 카카오 로그인
	MemberEntity loginKakao(String kakaoID);
	
	// 아이디 중복
	int idCheck(MemberDTO member_id) throws Exception;

	// 회원가입
	void register(MemberDTO user) throws Exception;

	// 아이디 찾기
	public MemberDTO find_id(MemberDTO dto);
	
	// 비밀번호 찾기
	public MemberDTO find_pass(MemberDTO dto);

	// 임시 비번 생성
	public String generate_pass(String member_id);
	
	// 비밀번호 업데이트
	public void update_pass(MemberDTO dto) throws Exception;
	
	// 회원정보 수정
	public void update_member(MemberDTO dto) throws Exception;
	
	// 회원탈퇴
	public void delete_member(MemberDTO dto) throws Exception;
	
	// 회원 탈퇴 - 회원 특정
	public int delete_check(MemberDTO dto) throws Exception;
	
	
//	//이메일발송
//	public void sendEmail(MemberDTO dto, String div) throws Exception;
//
//	//비밀번호찾기
//	public void findPw(HttpServletResponse resp, MemberDTO dto) throws Exception;
//
//	
	// sms
	public void certifiedPhoneNumber(String telnum, String numStr);

}

