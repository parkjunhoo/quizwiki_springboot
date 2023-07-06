
package com.multi.quizwiki.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.multi.quizwiki.common.FileUploadLogicService;
import com.multi.quizwiki.dto.EmailRequestDTO;
import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.member.entity.MemberEntity;
import com.multi.quizwiki.member.service.KakaoService;
import com.multi.quizwiki.member.service.MemberService;
import com.multi.quizwiki.member.service.SmsService;
import com.univcert.api.UnivCert;

import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Controller
public class MemberController {

	MemberService service;
	private KakaoService ms;
	SmsService sms;
	FileUploadLogicService fs;

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	public MemberController() {

	}

	@Autowired
	public MemberController(MemberService service, KakaoService ms, SmsService sms, FileUploadLogicService fs) {
		super();
		this.service = service;
		this.ms = ms;
		this.sms = sms;
		this.fs = fs;
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

	// 회원탈퇴
	@RequestMapping("/delete/user")
	public String show_delete_view() {
		return "thymeleaf/member/delete";
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
			System.out.println(session.getId());
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
		// System.out.println("로그아웃??");
		if (session != null) {
			System.out.println(session.getId());
			// System.out.println("로그아웃!!!");
			session.invalidate();
		}
		// System.out.println("로그아웃");
		return "redirect:/main";
	}

	// 카카오 로그인
	@RequestMapping(value = "/kakao/login", method = RequestMethod.GET)
	public String kakaoLogin(@RequestParam(value = "code", required = false) String code, Model model,
			HttpSession session) throws Exception {
		String view = "";
		String access_Token = ms.getAccessToken(code);

		HashMap<String, Object> userInfo = ms.getUserInfo(access_Token);

		System.out.println("userInfo : " + userInfo);
		// userInfo : {nickname=혜원, id=2877651179, email=vh1116@kakao.com}

		String loginname = (String) userInfo.get("id");
		System.out.println("loginname : " + loginname);
		// loginname : 2877651179 -> id값

		MemberEntity loginUser = service.loginKakao(loginname);
		System.out.println("loginUser : " + loginUser);
		// loginUser : null

		if (loginUser != null && loginUser.getKakaoID().equals(loginname)) {
			session.setAttribute("kakaoUser", MemberDTO.toDTO(loginUser));
			session.setAttribute("access_Token", access_Token);
			view = "redirect:/main";
		} else {
			// model.addAttribute("nickname", userInfo.get("nickname"));
			// model.addAttribute("email", userInfo.get("email"));
			// model.addAttribute("id", userInfo.get("id"));
			session.setAttribute("kakaoid", loginname);
			view = "redirect:/signupType";
		}

		return view;

	}

	// 카카오 로그아웃

	@RequestMapping(value = "/kakao/logout")
	public String kakaoLogout(HttpSession session) {

		String access_Token = (String) session.getAttribute("access_Token");
		System.out.println(access_Token);

		if (access_Token != null && !"".equals(access_Token)) {

			ms.kakaoLogout(access_Token);
			session.removeAttribute("access_Token");

			System.out.println("kakaoUser가 null이 아님 : " + session.getAttribute("kakaoUser"));

			session.removeAttribute("kakaoUser");
			System.out.println("kakaoUser가 null : " + session.getAttribute("kakaoUser"));

		} else {
			System.out.println("access_Token가 null");
			// return "redirect:/";
		}

		// return "index";
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

	// 회원 정보 보기
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public void infoGET(HttpSession session, Model model) throws Exception {

		// 세션 객체 안에 있는 ID정보 저장
		String member_id = (String) session.getAttribute("member_id");
		System.out.println("controller -> " + member_id);

		MemberDTO dto = service.read(member_id);
		System.out.println("controller -> " + dto);

		model.addAttribute("readDto", dto);
	}

	// 아이디 찾기 실행
	@RequestMapping(value = "/find_id.do", method = RequestMethod.POST)
	public String findIdAction(MemberDTO dto, Model model) {
		MemberDTO user = service.find_id(dto);
		System.out.println(dto);
		if (user == null) {
			System.out.println("null일 경우");
			model.addAttribute("check", 1);

		} else {
			System.out.println("null 아닐 경우");
			model.addAttribute("check", 0);
			model.addAttribute("member_id", user.getMember_id());
		}
		System.out.println(user);
		return "thymeleaf/member/login_id_forgot_find";
	}

	// 비밀번호 찾기 실행 - 그냥 비번 보여주기. 지양하고 싶다.
//	@RequestMapping(value = "/find_pass.do", method = RequestMethod.POST)
//	public String findPassAction(MemberDTO dto, Model model) {
//		MemberDTO user = service.find_pass(dto);
//		System.out.println(dto);
//		if (user == null) {
//			System.out.println("null");
//			model.addAttribute("check", 1);
//		} else {
//			System.out.println("null 아님");
//			model.addAttribute("check", 0);
//			model.addAttribute("update_id", user.getMember_id());
//			model.addAttribute("member_pass", user.getMember_pass());
//		}
//		return "thymeleaf/member/login_pass_forgot_find";
//
//	}

	// 비밀번호 찾기 get
	@RequestMapping(value = "/findpw", method = RequestMethod.GET)
	public void findPwGET() throws Exception {
	}

	// 비밀번호 찾기 post
	@RequestMapping(value = "/findpw", method = RequestMethod.POST)
	public String findPwPOST(@ModelAttribute MemberDTO member) throws Exception {
		service.find_pass(member);
		return "thymeleaf/member/login_pass_forgot";
	}

	// 비밀번호 변경
	@RequestMapping(value = "/update/pass", method = RequestMethod.POST)
	public String updatePass(MemberDTO dto, HttpSession session) throws Exception {
		// MemberDTO user = (MemberDTO) session.getAttribute("user");
		// System.out.println("-----------------------------------");
		System.out.println("비번 변경 dto 출력 : " + dto);
		// System.out.println("-----------------------------------");
		// System.out.println(member_pass);
		// dto.setMember_pass(member_pass);
		// user.setMember_pass(member_pass);
		service.update_pass(dto);
		session.setAttribute("user", dto);
		return "redirect:/mypage/modify";
	}

	// 회원정보 수정
	@RequestMapping(value = "/update/member", method = RequestMethod.POST)
	public String updateMember(MemberDTO dto, HttpSession session) throws Exception {
		System.out.println("회원 정보 수정 dto 출력 : " + dto);
		MultipartFile file = dto.getMember_image();
		if (file != null) {
			dto.setMember_photo(fs.uploadFile(file, "memberprofile"));
		}
		service.update_member(dto);
		session.setAttribute("user", dto);
		return "redirect:/mypage/modify";
	}

	// 회원탈퇴
	@RequestMapping(value = "/delete/user.do", method = RequestMethod.POST)
	public String deleteMember(MemberDTO dto, HttpSession session, RedirectAttributes rttr) throws Exception {

		MemberDTO member = (MemberDTO) session.getAttribute("user");
		String session_pass = member.getMember_pass();
		String input_pass = dto.getMember_pass();

		if (!(session_pass.equals(input_pass))) {
			rttr.addFlashAttribute("msg", false);
			return "redirect:/delete/user";
		}

		service.delete_member(dto);
		session.invalidate();
		return "redirect:/main";

	}

	// 회원탈퇴 - 회원 특정
	@ResponseBody
	@RequestMapping(value = "/delete/check", method = RequestMethod.POST)
	public int deleteMemberCheck(MemberDTO dto) throws Exception {
		int result = service.delete_check(dto);
		return result;
	}

	// sms
	// coolSMS 테스트 화면
	@GetMapping("/sms")
	public String mySms() {
		return "thymeleaf/member/sms";
	}

	// coolSMS 연결
	@GetMapping("/check/sendSMS")
	public @ResponseBody String sendSMS(@RequestParam(value = "to") String to) throws CoolsmsException {
		return sms.PhoneNumberCheck(to);
	}

	// 네이버 로그인 test login view
	@RequestMapping("/naver/login")
	public String naverLogin() {
		return "thymeleaf/member/naver_login";
	}

	// 네이버 로그인 test callback view
	@RequestMapping("/naver/callback")
	public String naverCallback() {
		return "thymeleaf/member/naver_callback";
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

}

/*
 * 
 * 
 * // 카카오 로그인
 * 
 * @RequestMapping(value = "/kakao/login", method = RequestMethod.GET) public
 * String kakaoLogin(@RequestParam(value = "code", required = false) String
 * code, Model model, HttpSession session) throws Exception { String view = "";
 * String access_Token = ms.getAccessToken(code); HashMap<String, Object>
 * userInfo = ms.getUserInfo(access_Token);
 * 
 * System.out.println("userInfo : " + userInfo); // userInfo : {nickname=혜원,
 * id=2877651179, email=vh1116@kakao.com}
 * 
 * String loginname = (String) userInfo.get("id");
 * System.out.println("loginname : " + loginname); // loginname : 2877651179 ->
 * id값
 * 
 * MemberEntity loginUser = service.loginKakao(loginname);
 * System.out.println("loginUser : " + loginUser); // loginUser : null
 * 
 * if (loginUser != null && loginUser.getKakaoID().equals(loginname)) {
 * System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
 * session.setAttribute("kakaoUser", MemberDTO.toDTO(loginUser));
 * System.out.println("로그인 - kakaoUser : " + MemberDTO.toDTO(loginUser)); //
 * session.setAttribute("access_Token", access_Token); view = "redirect:/main";
 * } else {
 * System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
 * model.addAttribute("nickname", userInfo.get("nickname"));
 * model.addAttribute("email", userInfo.get("email")); model.addAttribute("id",
 * userInfo.get("id")); //session.setAttribute("kakaoID", loginname); view =
 * "redirect:/signupType"; }
 * 
 * return view;
 * 
 * }
 * 
 * // 카카오 로그아웃
 * 
 * @RequestMapping(value="/kakao/logout") public String kakaoLogout(HttpSession
 * session) {
 * 
 * String access_Token = (String)session.getAttribute("access_Token");
 * 
 * 
 * if(access_Token != null && !"".equals(access_Token)){
 * ms.kakaoLogout(access_Token); session.removeAttribute("access_Token");
 * 
 * System.out.println("kakaoUser가 null이 아님 : "+session.getAttribute("kakaoUser")
 * );
 * 
 * session.removeAttribute("kakaoUser"); //Object kakaoUser =
 * session.getAttribute("kakaoUser");
 * 
 * System.out.println("kakaoUser가 null : "+session.getAttribute("kakaoUser"));
 * 
 * 
 * }else{ System.out.println("access_Token가 null"); //return "redirect:/"; }
 * 
 * //return "index"; return "redirect:/main"; }
 * 
 *
 */