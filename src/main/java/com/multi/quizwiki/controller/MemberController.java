package com.multi.quizwiki.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.member.service.MemberService;
import com.univcert.api.UnivCert;

@Controller
public class MemberController {

	
	MemberService service;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	public MemberController() {

	}
	
	@Autowired
	public MemberController(MemberService service) {
		super();
		this.service = service;
	}

	

	// 로그인
	@GetMapping("/login.do")
	public String show_login() {
		return "thymeleaf/member/login";
	}

	// 로그인 -> 아이디 찾기
	@RequestMapping("/findId.do")
	public String show_id_forgot() {
		return "thymeleaf/member/login_id_forgot";
	}

	// 로그인 -> 비밀번호 찾기
	@RequestMapping("/findPass.do")
	public String show_pass_forgot() {
		return "thymeleaf/member/login_pass_forgot";
	}

	// 해당 아이디 보여주기
	@RequestMapping("/findId")
	public String show_id() {
		return "thymeleaf/member/login_id_forgot_find";
	}

	// 임시 비밀번호 발급
	@RequestMapping("/findPass")
	public String show_pass() {
		return "thymeleaf/member/login_pass_forgot_find";
	}

	// 청소년 : 1, 대학생 : 2 - 회원 타입 구분
	@RequestMapping("/signupType")
	public String show_signup_type() {
		return "thymeleaf/member/signup_student_type";
	}

	// 청소년용 약관 동의서
	@RequestMapping("/signupAgreement1")
	public String show_signup_agreement1() {
		return "thymeleaf/member/signup_agreement1";
	}

	// 대학생용 약관 동의서
	@RequestMapping("/signupAgreement2")
	public String show_signup_agreement2() {
		return "thymeleaf/member/signup_agreement2";
	}

	// 회원가입 양식 작성 - 청소년
	@RequestMapping("/signup1")
	public String show_signup1() {
		return "thymeleaf/member/signup1";
	}

	// 회원가입 양식 작성 - 대학생
	@RequestMapping("/signup2")
	public String show_signup2() {
		return "thymeleaf/member/signup2";
	}

	// 로그인
	@PostMapping("/login.do")
	public ModelAndView login(MemberDTO loginUserInfo, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		MemberDTO user = service.login(loginUserInfo);
		String view = "";
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			view = "redirect:/main";
		} else {
			// System.out.println("등록되지 않은 사용자");
			view = "redirect:/login.do";
		}
		mav.setViewName(view);
		return mav;
	}

	// 로그아웃
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/main";
	}

	// 아이디 중복 확인
	@RequestMapping(value = "/idChk", method = RequestMethod.POST)
	@ResponseBody
	public int idCheck(MemberDTO member_id) throws Exception {
		int result = service.idCheck(member_id);
		return result;
	}

	// 회원가입
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public void get_register() throws Exception {
		logger.info("get signup");
	}

	// 회원가입
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String post_register(MemberDTO user) throws Exception {
		logger.info("post signup");
		service.register(user);
		return "redirect:/login.do";
	}

	
	// 아이디 찾기 실행
		@RequestMapping(value = "/find_id.do", method = RequestMethod.POST)
		public String findIdAction(MemberDTO dto, Model model) {
			MemberDTO member_id = service.find_id(dto);
			System.out.println(dto);
			if(member_id == null) {
				System.out.println("null일 경우");
				model.addAttribute("check", 1);
				
			} else {
				System.out.println("null 아닐 경우");
				model.addAttribute("check", 0);
				model.addAttribute("member_id", member_id.getMember_id());
			}
			System.out.println(member_id);
			return "thymeleaf/member/login_id_forgot_find";
		}
		
		
	// 대학교 메일 인증
	/*
	 * @RequestMapping(value = "https://univcert.com/api/v1/certify", produces =
	 * "application/json;charset=utf-8 ")
	 * 
	 * @ResponseBody public Map<String, Object> univ(String member_email, String
	 * universityName) throws IOException { System.out.println(member_email);
	 * System.out.println(universityName); Map<String, Object> msg =
	 * UnivCert.certify("aca6d520-24d6-43d2-a86e-08b8a7dc1762", member_email,
	 * universityName, true); // UnivCert.certify(API_KEY, email, universityName,
	 * univ_check) System.out.println("msg : " + msg); return msg;
	 * 
	 * }
	 */

	@RequestMapping(value = "/test", produces = "application/json;charset=utf-8 ")
	// @ResponseBody
	public String test(String email, String universityName) throws IOException {
		System.out.println("============email===========");
		UnivCert.certify("b7026b59-2d05-4165-be01-de304e8c76ae", email, universityName, true);
		return "test";
	}

	// sms 인증
//	@RequestMapping("/sendSMS")
//	@ResponseBody
//	public String sendSMS(String telnum) {
//
//		Random rand = new Random(); // 랜덤숫자 생성
//		String numStr = "";
//		for (int i = 0; i < 4; i++) {
//			String ran = Integer.toString(rand.nextInt(10));
//			numStr += ran;
//		}
//
//		service.certifiedPhoneNumber(telnum, numStr);
//
//		return numStr;
//	}

}
