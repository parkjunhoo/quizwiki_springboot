package com.multi.quizwiki.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.multi.quizwiki.dto.ProblemSearchDTO;
import com.multi.quizwiki.dto.ProblemSearchParameterDTO;
import com.multi.quizwiki.dto.ProblemSearchResultDTO;
import com.multi.quizwiki.search.service.SearchService;

import lombok.NoArgsConstructor;
import util.Utils;

@Controller
@NoArgsConstructor
@RequestMapping("/search/problem")
public class SearchController {
	
	private SearchService service;
	
	@Autowired
	public SearchController(SearchService service) {
		this.service = service;
	}
	
	@GetMapping("/list")
	public String show_problem_search() {
		return "thymeleaf/search/search_list";
	}
	
	@ResponseBody
	@PostMapping("/list")
	public ProblemSearchResultDTO problem_search(
			HttpServletRequest req,
			ProblemSearchParameterDTO param){
		
		int size = 12;
		int page = param.getPage();
		System.out.println(page);
		
		int totalCount = service.searchProblemCount(param);
		
		int totalPage = Utils.getTotalPage(totalCount, size);
		List<Integer> pages = Utils.makePagingSeq(page, 5, totalPage);
		
		int startLimit = (page-1)*size;
		
		param.setStartLimit(startLimit);
		param.setSizeLimit(size);
		List<ProblemSearchDTO> problemList = service.searchProblem(param);
		
		return new ProblemSearchResultDTO(
					pages,
					problemList,
					page,
					totalPage
				);
	};
	
}
