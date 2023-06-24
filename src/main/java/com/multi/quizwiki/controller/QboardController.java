package com.multi.quizwiki.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.quizwiki.qboard.dto.FileRequest;
import com.multi.quizwiki.qboard.dto.QboardDTO;
import com.multi.quizwiki.qboard.dto.SearchDto;
import com.multi.quizwiki.qboard.paging.PagingResponse;
import com.multi.quizwiki.qboard.service.FileServiceImpl;
import com.multi.quizwiki.qboard.service.QboardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import util.FileUtils;
@Controller
@Slf4j
@RequestMapping("quizwiki")
@RequiredArgsConstructor
public class QboardController {
	@Autowired
	QboardService qboardservice;
	@Autowired  
	private final FileServiceImpl fileService;
	
	 private final FileUtils fileUtils;
	
	
	
	
	@RequestMapping("/qboard/list")
	public String show_qboard_list() {
		return "thymeleaf/qboard/qboard_list";
	}
	@PostMapping("/qboard/save.do")
	public String save(QboardDTO qboard, Model model) {
			Long qboard_id = qboardservice.save(qboard); //게시글 삽입 게시글은 db에 저장되는데 
			// 파일이 null 이 뜨는 듯
			List<FileRequest> files = fileUtils.uploadFiles(qboard.getFiles()); //디스크에 파일 업로드
			log.info("파일 컨트롤러 실행" +qboard.getFiles());
	        fileService.saveFiles(qboard_id, files); // 업로드 된 정보를 db에 저장
		 return "thymeleaf/qboard/qboard_list";

	}
	
	 // 쿼리 스트링 파라미터를 Map에 담아 반환
    private Map<String, Object> queryParamsToMap(final SearchDto queryParams) {
        Map<String, Object> data = new HashMap<>();
        data.put("page", queryParams.getPage());
        data.put("recordSize", queryParams.getRecordSize());
        data.put("pageSize", queryParams.getPageSize());
        data.put("keyword", queryParams.getKeyword());
        data.put("searchType", queryParams.getSearchType());
        return data;
    }

	
	@RequestMapping("/qboard/write.do")
	public String QboardWrite(@RequestParam(value="qboard_id", required = false) Long qboard_id, Model model) {
	
		if(qboard_id != null) {
			
		
				 QboardDTO qboard = qboardservice.getQboardDetail(qboard_id);
				 model.addAttribute("qboard",qboard);
		}  
			 
		return "thymeleaf/qboard/qboard_write";
	}
	
	
	  @GetMapping("/qboard/list.do") 
	  public String QboardList(@ModelAttribute("params") SearchDto params, Model model) {
		  log.info("list.do 실행");
		  PagingResponse<QboardDTO> qboardlist = qboardservice.getBoardList(params);
		  model.addAttribute("qboardlist",qboardlist); 
	  		
	  	return "thymeleaf/qboard/qboard_list"; 
	  
	  }
	  @GetMapping("/qboard/read.do")
		 public String QboardDetail(@RequestParam(value="qboard_id",required = false) Long qboard_id, Model model ) {
				/*	
				 * if (qboard_id == null) { return "redirect:/qboard/list"; }
				 */
			 QboardDTO qboard = qboardservice.getQboardDetail(qboard_id);
			 model.addAttribute("qboard", qboard);
			
			return "thymeleaf/qboard/qboard_read";
		 }
		 	
	@GetMapping("/qboard/delete")
	public String deleteBoard(@RequestParam Long qboard_id) {
		qboardservice.deleteQboard(qboard_id);
		
		return "redirect:/quizwiki/qboard/list.do";
	}
	
	@PostMapping("qboard/update.do")
	public String updateQboard(QboardDTO qboard) {
			qboardservice.update(qboard);
			return "redirect:/quizwiki/qboard/list.do";
		
	}
	
 	
}
