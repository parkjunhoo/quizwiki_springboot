package com.multi.quizwiki.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.multi.quizwiki.dto.PboardDTO;
import com.multi.quizwiki.mypage.dto.InquryDTO;
import com.multi.quizwiki.mypage.dto.InquryReplyDTO;
import com.multi.quizwiki.mypage.dto.NoteDTO;
import com.multi.quizwiki.mypage.dto.PointDTO;
import com.multi.quizwiki.mypage.service.MypageService;
import com.multi.quizwiki.qboard.dto.QboardDTO;
import com.multi.quizwiki.service.MypageFileService;

@Controller
public class MypageController {
	MypageService service;
	MypageFileService fileservice;
	public MypageController() {
		
	}
	
	@Autowired
	public MypageController(MypageService service,MypageFileService fileservice) {
		super();
		this.service = service;
		this.fileservice = fileservice;
	}
	@RequestMapping("/mypage/mycontentroom")
	public String mycontentroom() {
		return "thymeleaf/mypage/mycontentroom";
	}
	//내문제리스트, 갯수
	@RequestMapping("/mypage/myproblem")
	public String cash(String member_id,Model model,@RequestParam(value="state",required = false,defaultValue = "d") String state,String startday,String endday) {
		String pboardcount = service.pboardcount(member_id);
		model.addAttribute("pboardcount", pboardcount);
		if(state.equals("all")) {
			List<PboardDTO> pboardlist = service.pboardread(member_id);
			model.addAttribute("pboardlist", pboardlist);
		}else {
			List<PboardDTO> pboardlist = service.pboardsearch(startday, endday, member_id);
			model.addAttribute("pboardlist", pboardlist);
		}
		System.out.println(startday+endday+member_id);
		return "thymeleaf/mypage/myproblem";
	}
	@RequestMapping("/mypage/modify")
	public String modify() {
		return "thymeleaf/mypage/modify";
	}
	//내질문
	@RequestMapping("/mypage/myquestion")
	public String myquestion(String member_id,Model model) {
		List<QboardDTO> qboardlist=service.qboardread(member_id);
		model.addAttribute("qboardlist", qboardlist);
		System.out.println(qboardlist);
		return "thymeleaf/mypage/myquestion";
	}
	//내오답노트 
	@RequestMapping("/mypage/note")
	public String payment(String member_id,Model model,@RequestParam(value="state",required = false,defaultValue = "d") String state,String startday,String endday) {
		
		String notecount = service.notecount(member_id);
		model.addAttribute("notecount", notecount);
		List<NoteDTO> notelist=null;
		if(state.equals("all")) {
			notelist = service.noteread(member_id);
			model.addAttribute("notelist", notelist);	
		}else {
			notelist = service.notesearch(startday, endday, member_id);
			model.addAttribute("notelist", notelist);
		}
		return "thymeleaf/mypage/note";
	}
	//포인트 내역보기
	@RequestMapping("/mypage/point")
	public String point(String member_id,Model model,@RequestParam(value="state",required = false,defaultValue = "d") String state,String startday,String endday) {
		System.out.println(member_id+startday+endday);
		String pointtotal=service.pointtotal(member_id);
		model.addAttribute("pointtotal", pointtotal);
		List<PointDTO> pointlist=null;
		if(state.equals("all")) {
			pointlist=	service.pointread(member_id);
			model.addAttribute("pointlist", pointlist);
		}else {
			pointlist=service.pointsearch(startday, endday,member_id);
			model.addAttribute("pointlist", pointlist);
			
		}
		System.out.println("컨트롤러=>"+pointlist);
		return "thymeleaf/mypage/point";
	}

	//문의 사항 
	@RequestMapping("/mypage/ask")//1:1문의사항 read
	public String ask(@RequestParam("member_id")String member_id,@RequestParam("inqury_category")String inqury_category,Model model) {
		System.out.println(inqury_category);
		List<InquryDTO> inqurylist = service.inquryread(member_id, inqury_category);
		model.addAttribute("inqurylist", inqurylist);
		System.out.println("컨트롤러"+inqurylist);
		return "thymeleaf/mypage/ask";
	}
	@RequestMapping("/mypage/suggest")//오류문항 read
	public String suggest(@RequestParam("member_id")String member_id,@RequestParam("inqury_category")String inqury_category,Model model) {
		List<InquryDTO> inqurylist = service.inquryread(member_id, inqury_category);
		model.addAttribute("inqurylist", inqurylist);
		return "thymeleaf/mypage/suggest";
	}
	@RequestMapping("/mypage/wronganserWrite")//오류문항 작성 페이지 
	public String wronganserWrite() {
		return "thymeleaf/mypage/wronganserWrite";
	}
	@RequestMapping("/mypage/otoWrite")//1:1문의사항 작성 페이지
	public String otoWrite() {
		return "thymeleaf/mypage/otoWrite";
	}
	@RequestMapping("/mypage/update")//1:1문의사항 업데이트 페이지
	public String otoupdatepage(String inqury_id,Model model) {
		InquryDTO oto =service.inqurydetail(inqury_id);
		model.addAttribute("oto",oto);
		return "thymeleaf/mypage/otoUpdate";
	}
	@RequestMapping("/mypage/oto/insert")//1:1문의사항 insert
	public String otoinsert(InquryDTO inqurydto,RedirectAttributes redirect) {
		service.inquryinsert(inqurydto);
		redirect.addAttribute("member_id","test");
		redirect.addAttribute("inqury_category","1:1문의");
		return "redirect:/mypage/ask";
	}
	@RequestMapping("/mypage/wronganser/insert")//오류문항 insert  
	public String wronganserinsert(InquryDTO inqurydto) {
		service.inquryupdate(inqurydto);
		return "redirect:/mypage/suggest";
	}
	@RequestMapping("/mypage/oto/delete")//1:1문의사항 삭제  
	public String otodelte(String inqury_id,RedirectAttributes redirect) {
		service.inqurydelete(inqury_id);
		redirect.addAttribute("member_id","test");
		redirect.addAttribute("inqury_category","1:1문의");
		return "redirect:/mypage/ask";
	}
	@RequestMapping("/mypage/wronganser/delete")//오류신고 삭제  
	public String wronganserdelete(String member_id) {
		service.inqurydelete(member_id);
		return "redirect:/mypage/suggest";
	}
	@RequestMapping("/mypage/oto/update")//1:1문의사항 업데이트 
	public String otoupdate(InquryDTO inqurydto,RedirectAttributes redirect) {
		System.out.println("수정본"+inqurydto);
		service.inquryupdate(inqurydto);
		System.out.println("컨트롤러 수정본"+service.inquryupdate(inqurydto));
		redirect.addAttribute("member_id","test");
		redirect.addAttribute("inqury_category","1:1문의");
		return "redirect:/mypage/ask";
	}
	@RequestMapping("/mypage/wronganser/update")//오류문항 업데이트   
	public String wronganserupdate(InquryDTO inqurydto) {
		service.inquryupdate(inqurydto);
		return "redirect:/mypage/suggest";
	}
	@RequestMapping("/mypage/oto2")//1:1문의내역 자세히보기, 답변
	public String oto2(String inqury_id,Model model) {
		InquryDTO oto = service.inqurydetail(inqury_id);
		InquryReplyDTO inquryreply = service.inquryreply(inqury_id);
		model.addAttribute("oto", oto);
		model.addAttribute("inquryreply", inquryreply);
		return "thymeleaf/mypage/oto";
	}
	@RequestMapping("/mypage/wronganser")//오답신고 자세히보기,답변
	public String wronganser(String inqury_id, Model model) {
		InquryDTO wronganser = service.inqurydetail(inqury_id);
		InquryReplyDTO inquryreply = service.inquryreply(inqury_id);
		model.addAttribute("wronganser", wronganser);
		model.addAttribute("inquryreply", inquryreply);
		return "thymeleaf/mypage/wronganser";
	}
}
