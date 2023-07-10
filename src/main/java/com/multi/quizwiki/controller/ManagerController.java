package com.multi.quizwiki.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.manager.dto.RecoCategoryDTO;
import com.multi.quizwiki.manager.dto.RecoProblemDTO;
import com.multi.quizwiki.manager.dto.SolvCountDTO;
import com.multi.quizwiki.manager.service.ManagerService;

@Controller
@RequestMapping("studymanager")
public class ManagerController {
	
	private ManagerService service;
	
	@Autowired
	public ManagerController(ManagerService service) {
		this.service = service;	
	}
	
	@GetMapping("/mymanager")
	public String show_mymanager(HttpServletRequest req,Model model) {
		
		MemberDTO member = util.Utils.getSessionUser(req);
		if(member == null) {
			return "redirect:/login.do";
		}
		
		String memberId = member.getMember_id();
		
		
		List<RecoCategoryDTO> bestList = service.bestCate(memberId);
		List<RecoCategoryDTO> worstList = service.worstCate(memberId);
		
		List<SolvCountDTO> solvCountList = service.findSolvCountBySubjectName(memberId);
		List<SolvCountDTO> solvCountListByProblemCate = service.findSolvCountByProblemCateName(memberId);
		model.addAttribute("solvCountList", solvCountList);
		model.addAttribute("solvCountListByProblemCate", solvCountListByProblemCate);
		model.addAttribute("bestList", bestList);
		model.addAttribute("worstList", worstList);
		return "thymeleaf/manager/mymanager";
	}
	
	@GetMapping("/recommand")
	public String show_recommand(HttpServletRequest req, Model model) {
		
		MemberDTO member = util.Utils.getSessionUser(req);
		if(member == null) {
			return "redirect:/login.do";
		}
		
		String memberId = member.getMember_id();
		
		List<RecoProblemDTO> problemList = service.recommandProblem(memberId, true, 10);
		model.addAttribute("problemList",problemList);
		
		return "thymeleaf/manager/cbt";
	}
}
