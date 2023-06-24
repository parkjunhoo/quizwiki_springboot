package com.multi.quizwiki.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.Utils;

@Controller
public class MainPageController {
	
	public MainPageController() {
		
	}
	
	@RequestMapping("/main")
	public String show_mainpage() {
		return "thymeleaf/mainpage/mainpage";
	}
	
	
	
	
	
	
	
	
	
	///////임시
	@ResponseBody
	@GetMapping("/get/logincheck")
	public boolean login_check(HttpServletRequest req) {
		return Utils.loginCheck(req);
	}
}
