
package com.multi.quizwiki.member.dao;

import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.member.entity.MemberEntity;

public interface MemberDAO {
	
	// 로그인
	MemberDTO login(MemberDTO loginUser);
	
	// 카카오 로그인
	MemberEntity loginKakao(String kakaoID);
	
	// 아이디 중복 체크
	int idCheck(MemberDTO member_id) throws Exception;
	
	// 회원가입
	void register(MemberDTO user) throws Exception;
	
	// 회원정보조회
	MemberDTO read(String member_id) throws Exception;
	
	
	// 비번 변경 - 임시 비번으로
	int updatePw(MemberDTO dto) throws Exception;
	
	
	// 아이디 찾기
	public MemberDTO find_id(MemberDTO dto);
	
	// 비밀번호 찾기
	public MemberDTO find_pass(MemberDTO dto);
	
	// 비밀번호 변경
	public void update_pass(MemberDTO dto) throws Exception;
	
	// 회원정보 수정
	public void update_member(MemberDTO dto) throws Exception;
	
	// 회원 탈퇴
	public void delete_member(MemberDTO dto) throws Exception;
	
	// 회원 탈퇴 - 회원 특정하기
	public int delete_check(MemberDTO dto) throws Exception;
	
	// 임시 비번
	//public void updatePass(String member_id, String member_pass);
	
}

