package com.multi.quizwiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.quizwiki.problem.entity.ProblemEntity;
import com.multi.quizwiki.problem.service.ProblemService;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@RequestMapping("popup")
@Controller
public class PopupController {
	
	private ProblemService problemService;
	
	@Autowired
	public PopupController(ProblemService problemService) {
		this.problemService = problemService;
	}
	
	@GetMapping("/upload/image")
	public String show_image_upload() {
		
		return "thymeleaf/popup/image_upload_popup";
	}
	
	@GetMapping("/problem/preview")
	public String show_problem_preview(Model model , int no) {
		ProblemEntity problem = problemService.problem_findByProblemId(no);
		model.addAttribute("problem",problem);
		
		return "thymeleaf/popup/problem_preview_popup";
	}
}
