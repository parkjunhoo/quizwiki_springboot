package com.multi.quizwiki.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.dto.PboardDTO;
import com.multi.quizwiki.dto.ProblemDTO;
import com.multi.quizwiki.main.service.MainService;
import com.multi.quizwiki.member.service.MemberService;
import com.multi.quizwiki.qboard.entity.QboardEntity;

import lombok.NoArgsConstructor;
import util.Utils;

@Controller
@NoArgsConstructor
public class MainPageController {
	
	MemberService memberService;
	
	MainService mainService;
	
	@Autowired
	public MainPageController(MemberService memberService , MainService mainService) {
		this.memberService = memberService;
		this.mainService = mainService;
	}
	
	@RequestMapping("/main")
	public String show_mainpage(Model model) {
		
		List<PboardDTO> pboardTopList = mainService.pboard_findOrderByLikeCount(10);
		List<QboardEntity> qboardTopList = mainService.findTop10ByDeleteYnNotOrderByViewCountDesc();
		List<ProblemDTO> problemTopList = mainService.problem_findOrderByLike(10);
		
		model.addAttribute("pboardTopList", pboardTopList);
		model.addAttribute("qboardTopList", qboardTopList);
		model.addAttribute("problemTopList", problemTopList);
		
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
		
		String inputId = member.getMember_id().trim();
		String inputPass = member.getMember_pass().trim();
		member.setMember_id(inputId);
		member.setMember_pass(inputPass);
		
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
