package com.multi.quizwiki.member.service;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.member.dao.MemberDAO;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class MemberServiceImpl implements MemberService {
	
	MemberDAO dao;
	
	public MemberServiceImpl() {
		
	}
	
	@Autowired
	public MemberServiceImpl(MemberDAO dao) {
		super();
		this.dao = dao;
	}

	// 로그인
	@Override
	public MemberDTO login(MemberDTO loginUser) {
		//user가 db인증 후에 받은 결과
		MemberDTO user= dao.login(loginUser);
		System.out.println("서비스==>"+user);
		return user;
	}
	
	// 아이디 중복
	@Override
	public int idCheck(MemberDTO member_id) throws Exception {
		int result = dao.idCheck(member_id);
		return result;
	}

	// 회원가입
	@Override
	public void register(MemberDTO user) throws Exception {
		
		//System.out.println(user);
		user.setMember_point(2000); // 회원가입 시 2000 포인트를 제공
		
		if (user.getMember_extra_addr() == "") { // 주소 참고항목란
			user.setMember_extra_addr("참고항목 없음");
		}
		if (user.getMember_mkt_opt() == null) { // 마케팅 수신 옵션 아무것도 체크하지 않았을 떄
			user.setMember_mkt_opt("마케팅 수신 미동의");
		}
		
		if (user.getUniversityName() == null) { // 회원 타입 지정
	        user.setMember_type("1"); // 고등학생
	    } 
		if (user.getUniversityName() != null) { // 회원 타입 지정
	        user.setMember_type("2"); // 대학생
	    }
		System.out.println(user);
		dao.register(user);
		
	}
	

	// 아이디 찾기
	@Override
	public MemberDTO find_id(MemberDTO dto) {
		MemberDTO id = dao.find_id(dto);
		return id;
	}

	@Override
	public void certifiedPhoneNumber(String telnum, String numStr) {
		
		
		  String api_key = "NCSHJUW5DCMXSS7Y";
          String api_secret = "UXVPXD24NSMYAELLTR5BW4RLVMSRAVVL";
          Message coolsms = new Message(api_key, api_secret);

        
          HashMap<String, String> params = new HashMap<String, String>();
          params.put("to", telnum);    
          params.put("from", "01079196032");   
          params.put("type", "SMS");
          params.put("text", "작성할내용 "+numStr);
          params.put("app_version", "test app 1.2"); // application name과 version

          try {
              JSONObject obj = (JSONObject) coolsms.send(params);
              System.out.println(obj.toString());
          } catch (CoolsmsException e) {
              System.out.println(e.getMessage());
              System.out.println(e.getCode());
          }

	}
	
	
	

	
}
