
package com.multi.quizwiki.member.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.quizwiki.dto.EmailRequestDTO;
import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.member.dao.MemberDAO;
import com.multi.quizwiki.member.entity.MemberEntity;
import com.multi.quizwiki.member.repository.MemberRepository;

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
		//System.out.println("서비스단 : "+user);
		return user;
	}
	
	// 카카오 로그인
	@Override
	public MemberEntity loginKakao(String kakaoID) {
		 return dao.loginKakao(kakaoID);
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
		
		user.setMember_state(1); // 회원가입 시 state는 1, 탈퇴 시 0
		
		if (user.getMember_extra_addr() == "") { // 주소 참고항목란
			user.setMember_extra_addr("참고항목 없음");
		}
		if (user.getMember_mkt_opt() == null) { // 마케팅 수신 옵션 아무것도 체크하지 않았을 떄
			user.setMember_mkt_opt("마케팅 수신 미동의");
		}
		
		if (user.getUniversity_name() == null) { // 회원 타입 지정
	        user.setMember_type("1"); // 고등학생
	    } 
		if (user.getUniversity_name() != null) { // 회원 타입 지정
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
	
	// 비밀번호 찾기
	@Override
	public MemberDTO find_pass(MemberDTO dto) {
		MemberDTO pass = dao.find_pass(dto);
		return pass;
	}
	
	// 비밀번호 업데이트
	@Override
	public void update_pass(MemberDTO dto) throws Exception {
		dao.update_pass(dto);
		
	}
	
	// 회원정보 업데이트
	@Override
	public void update_member(MemberDTO dto) throws Exception {
		dao.update_member(dto);
	}

	// 회원탈퇴
	@Override
	public void delete_member(MemberDTO dto) throws Exception {
		dao.delete_member(dto);
	}
	
	// 회원 탈퇴 - 회원 특정
	@Override
	public int delete_check(MemberDTO dto) throws Exception {
		int result = dao.delete_check(dto);
		return result;
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

	@Override
	public String generate_pass(String member_id) {
		// TODO Auto-generated method stub
		return null;
	}

	
//	@SuppressWarnings("deprecation")
//	@Override
//	public void sendEmail(MemberDTO dto, String div) throws Exception {
//		// Mail Server 설정
//		String charSet = "utf-8";
//		String hostSMTP = "smtp.naver.com"; 
//		String hostSMTPid = "hyen1616@naver.com";
//		String hostSMTPpwd = "qkdrnahdla9595!";
//
//		// 보내는 사람 EMail, 제목, 내용
//		String fromEmail = "hyen1616@naver.com";
//		String fromName = "QuizWiki";
//		String subject = "";
//		String msg = "";
//
//		if(div.equals("findpw")) {
//			subject = "QuizWiki 임시 비밀번호 입니다.";
//			msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
//			msg += "<h3 style='color: blue;'>";
//			msg += dto.getMember_id() + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.</h3>";
//			msg += "<p>임시 비밀번호 : ";
//			msg += dto.getMember_pass() + "</p></div>";
//		}
//
//		// 받는 사람 E-Mail 주소
//		String mail = dto.getEmail();
//		try {
//			HtmlEmail email = new HtmlEmail();
//			email.setDebug(true);
//			email.setCharset(charSet);
//			email.setSSL(true);
//			email.setHostName(hostSMTP);
//			email.setSmtpPort(587); 
//
//			email.setAuthentication(hostSMTPid, hostSMTPpwd);
//			email.setTLS(true);
//			email.addTo(mail, charSet);
//			email.setFrom(fromEmail, fromName, charSet);
//			email.setSubject(subject);
//			email.setHtmlMsg(msg);
//			email.send();
//		} catch (Exception e) {
//			System.out.println("메일발송 실패 : " + e);
//		}
//	}
//
//	@Override
//	public void findPw(HttpServletResponse resp, MemberDTO dto) throws Exception {
//	
//
//	}
}

