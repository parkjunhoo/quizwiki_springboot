package com.multi.quizwiki.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.member.service.MemberService;

import lombok.NoArgsConstructor;
import util.Utils;

@Controller
@NoArgsConstructor
public class MainPageController {
	
	MemberService memberService;
	
	@Autowired
	public MainPageController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("/main")
	public String show_mainpage(Model model) {
		return "thymeleaf/mainpage/mainpage";
	}
	
	
	
	
	
	
	
	
	
	///////임시/////////////////////////
	@ResponseBody
	@GetMapping("/get/logincheck")
	public boolean login_check(HttpServletRequest req) {
		return Utils.loginCheck(req);
	}
	
	/////////임시///////////////////////
	@PostMapping("/post/login")
	@ResponseBody
	public JsonNode test_login(MemberDTO member, HttpServletRequest request) {
		
		String msg = "false";
		
		MemberDTO user = memberService.login(member);
		
		if(user != null){
			request.getSession().setAttribute("user", user);
			msg = "true";
		}else{
			return util.Utils.getJsonStringAsResForm(null, msg);
		}
		
		return util.Utils.getJsonStringAsResForm(user.getMember_id(), msg);
	}
}
