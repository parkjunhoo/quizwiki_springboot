
package com.multi.quizwiki.member.service;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.HtmlEmail;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
		// user가 db인증 후에 받은 결과
		MemberDTO user = dao.login(loginUser);
		// System.out.println("서비스단 : "+user);
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

		// System.out.println(user);
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

	// 회원 정보 조회
	@Override
	public MemberDTO read(String member_id) throws Exception {
		System.out.println("service -> read");
		MemberDTO dto = null;

		try {
			dto = dao.read(member_id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	// 아이디 찾기
	@Override
	public MemberDTO find_id(MemberDTO dto) {
		MemberDTO id = dao.find_id(dto);
		return id;
	}

	// 비밀번호 찾기
	@Override
	public void find_pass( MemberDTO dto) throws Exception {
		//PrintWriter out = response.getWriter();
		// 임시 비밀번호 생성
		String pw = "";
		for (int i = 0; i < 12; i++) {
			pw += (char) ((Math.random() * 26) + 97);
		}
		//System.out.println("임시 비번으로 세팅 전 : "+dto);
		dto.setMember_pass(pw);
		//System.out.println("임시 비번으로 세팅 후 : "+dto);
		// 비밀번호 변경
		//System.out.println("db 비번 변경 전 : "+dto);
		dao.update_pass(dto);
		//System.out.println("db 비번 변경 후 : "+dto);
		// 비밀번호 변경 메일 발송
		sendEmail(dto, "findpw");
		//System.out.println("******메일 발송 완료******");

	//	out.print("이메일로 임시 비밀번호를 발송하였습니다.");
		//out.close();
	}

	// 비번 이멜
	@Override
	public void sendEmail(MemberDTO dto, String div) throws Exception {
		// Mail Server 설정
		String charSet = "utf-8";
		String hostSMTP = "smtp.naver.com";
		String hostSMTPid = "hyen1616@naver.com";
		String hostSMTPpwd = "qkdrnahdla9595!";

		// 보내는 사람 EMail, 제목, 내용
		String fromEmail = "hyen1616@naver.com";
		String fromName = "QuizWiki";
		String subject = "";
		String msg = "";

		if (div.equals("findpw")) {
			subject = "QuizWiki 임시 비밀번호 입니다.";
			msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
			msg += "<h3 style='color: blue;'>";
			msg += dto.getMember_id() + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.</h3>";
			msg += "<p>임시 비밀번호 : ";
			msg += dto.getMember_pass() + "</p></div>";
		}

		// 받는 사람 E-Mail 주소
		String mail = dto.getEmail();
		try {
			HtmlEmail email = new HtmlEmail();
			email.setDebug(true);
			email.setCharset(charSet);
			//email.setSSL(true);
			email.setSSLOnConnect(true);
			email.setHostName(hostSMTP);
			email.setSmtpPort(587);

			email.setAuthentication(hostSMTPid, hostSMTPpwd);
			//email.setTLS(true);
			email.setStartTLSEnabled(true);
			email.addTo(mail, charSet);
			email.setFrom(fromEmail, fromName, charSet);
			email.setSubject(subject);
			email.setHtmlMsg(msg);
			email.send();
		} catch (Exception e) {
			System.out.println("메일발송 실패 : " + e);
		}

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

	// 회원 정보 수정 - 프로필 이미지
	@Override
	public int insert(MemberDTO dto, MultipartFile file, String realpath, String filename) {
		// TODO Auto-generated method stub
		return 0;
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
		params.put("text", "작성할내용 " + numStr);
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


}
