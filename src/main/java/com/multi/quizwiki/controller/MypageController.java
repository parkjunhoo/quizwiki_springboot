package com.multi.quizwiki.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.dto.PboardDTO;
import com.multi.quizwiki.dto.SolvDTO;
import com.multi.quizwiki.mypage.dto.InquryDTO;
import com.multi.quizwiki.mypage.dto.InquryFileDTO;
import com.multi.quizwiki.mypage.dto.InquryReplyDTO;
import com.multi.quizwiki.mypage.dto.NoteDTO;
import com.multi.quizwiki.mypage.dto.PointDTO;
import com.multi.quizwiki.mypage.dto.ProblemInquiryDTO;
import com.multi.quizwiki.mypage.dto.solvSearchParam;
import com.multi.quizwiki.mypage.service.MypageFileService;
import com.multi.quizwiki.mypage.service.MypageService;
import com.multi.quizwiki.pboard.service.PboardService;
import com.multi.quizwiki.problem.entity.ProblemCateEntity;
import com.multi.quizwiki.problem.service.ProblemService;
import com.multi.quizwiki.qboard.dto.QboardDTO;
import com.multi.quizwiki.solv.service.SolvService;

import util.Utils;

@Controller
public class MypageController {
	MypageService service;
	MypageFileService fileservice;
	SolvService solvservice;
	PboardService problemservice;

	public MypageController() {

	}

	@Autowired
	public MypageController(MypageService service, MypageFileService fileservice, SolvService solvservice,
			PboardService problemservice) {
		super();
		this.service = service;
		this.fileservice = fileservice;
		this.solvservice = solvservice;
		this.problemservice = problemservice;
	}

	@RequestMapping("/mypage/mycontentroom") // 내가 푼 문제 검색
	public String solvsearch(Model model, HttpServletRequest req,
			@RequestParam(defaultValue = "null") String problemCateId,
			@RequestParam(required = false) Boolean solvRight, @RequestParam(defaultValue = "") String startDate,
			@RequestParam(defaultValue = "") String endDate, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, String id) throws ParseException {

		MemberDTO member = util.Utils.getSessionUser(req);
		if (member == null) {
			return "redirect:/login.do";
		}

		String memberId = member.getMember_id();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date minDate = null;
		java.sql.Date maxDate = null;

		Integer problemCate = problemCateId.equals("null") ? null : Integer.parseInt(problemCateId);

		if (!startDate.equals("") && !endDate.equals("")) {
			Date mDate = (Date) format.parse(startDate);
			Date MDate = (Date) format.parse(endDate);
			minDate = new java.sql.Date(mDate.getTime());
			maxDate = new java.sql.Date(MDate.getTime());
		}
		int count = solvservice.findCountByFilter(memberId, problemCate, solvRight, minDate, maxDate);
		int totalPage = Utils.getTotalPage(count, size);// 전체 페이지 갯수
		List<Integer> pages = Utils.makePagingSeq(page, 5, totalPage);// 클릭할 숫자들
		List<SolvDTO> solvs = solvservice.findByFilter(memberId, problemCate, solvRight, minDate, maxDate, size, page);
		List<ProblemCateEntity> problemCateList = problemservice.problemCate_findAll();
		model.addAttribute("pages", pages);
		model.addAttribute("solvs", solvs);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("solvParam", new solvSearchParam(problemCateId, solvRight, startDate, endDate));
		model.addAttribute("problemCateList", problemCateList);
		model.addAttribute("id", id);
		
		System.out.println("totalPage===>" + totalPage);
		System.out.println("count===>" + count);
		System.out.println("pages===>" + pages);
		return "thymeleaf/mypage/mycontentroom";
	}

	// 내문제리스트, 갯수
	@RequestMapping("/mypage/myproblem")
	public String myproblem(HttpServletRequest req, Model model,
			@RequestParam(value = "state", required = false, defaultValue = "d") String state, String startday,
			String endday, String id) {
		MemberDTO member = util.Utils.getSessionUser(req);
		if (member == null) {
			return "redirect:/login.do";
		}

		String member_id = member.getMember_id();
		String pboardcount = service.pboardcount(member_id);
		model.addAttribute("pboardcount", pboardcount);
		model.addAttribute("id", id);
		model.addAttribute("startday", startday);
		model.addAttribute("endday", endday);
		if (state.equals("all")) {
			List<PboardDTO> pboardlist = service.pboardread(member_id);
			model.addAttribute("pboardlist", pboardlist);
		} else {
			List<PboardDTO> pboardlist = service.pboardsearch(startday, endday, member_id);
			model.addAttribute("pboardlist", pboardlist);
		}
		return "thymeleaf/mypage/myproblem";
	}

	@RequestMapping("/mypage/modify")
	public String modify(String id,Model model) {
		model.addAttribute("id", id);
		return "thymeleaf/mypage/modify";
	}

	// 내질문
	@RequestMapping("/mypage/myquestion")
	public String myquestion(HttpServletRequest req, Model model, String id) {
		MemberDTO member = util.Utils.getSessionUser(req);
		if (member == null) {
			return "redirect:/login.do";
		}

		String member_id = member.getMember_id();
		List<QboardDTO> qboardlist = service.qboardread(member_id);
		model.addAttribute("qboardlist", qboardlist);
		model.addAttribute("id", id);
		return "thymeleaf/mypage/myquestion";
	}

	// 내오답노트
	@RequestMapping("/mypage/note")
	public String payment(HttpServletRequest req, Model model,
			@RequestParam(value = "state", required = false, defaultValue = "d")
			String state, 
			String startday,
			String endday,
			String id) {
		System.out.println("아이디=>" + id);
		MemberDTO member = util.Utils.getSessionUser(req);
		if (member == null) {
			return "redirect:/login.do";
		}

		String member_id = member.getMember_id();
		String notecount = service.notecount(member_id);
		model.addAttribute("notecount", notecount);
		model.addAttribute("id", id);
		model.addAttribute("startday", startday);
		model.addAttribute("endday", endday);
		List<NoteDTO> notelist = null;
		if (state.equals("all")) {
			notelist = service.noteread(member_id);
			model.addAttribute("notelist", notelist);
		} else {
			notelist = service.notesearch(startday, endday, member_id);
			model.addAttribute("notelist", notelist);
		}
		return "thymeleaf/mypage/note";
	}

	// 포인트 내역보기
	@RequestMapping("/mypage/point")
	public String point(HttpServletRequest req, Model model,
			@RequestParam(value = "state", required = false, defaultValue = "d") String state, String startday,
			String endday, String id) {
		MemberDTO member = util.Utils.getSessionUser(req);
		if (member == null) {
			return "redirect:/login.do";
		}

		String member_id = member.getMember_id();
		String pointtotal = service.pointtotal(member_id);
		model.addAttribute("pointtotal", pointtotal);
		model.addAttribute("id", id);
		model.addAttribute("startday", startday);
		model.addAttribute("endday", endday);
		List<PointDTO> pointlist = null;
		if (state.equals("all")) {
			pointlist = service.pointread(member_id);
			model.addAttribute("pointlist", pointlist);
		} else {
			pointlist = service.pointsearch(startday, endday, member_id);
			model.addAttribute("pointlist", pointlist);

		}
		System.out.println("컨트롤러=>" + pointlist);
		return "thymeleaf/mypage/point";
	}

	// 문의 사항
	@RequestMapping("/mypage/ask") // 1:1문의사항 read
	public String ask(HttpServletRequest req, @RequestParam("inqury_category") String inqury_category, Model model,
			String id) {
		MemberDTO member = util.Utils.getSessionUser(req);
		if (member == null) {
			return "redirect:/login.do";
		}

		String member_id = member.getMember_id();
		List<InquryDTO> inqurylist = service.inquryread(member_id, inqury_category);
		model.addAttribute("inqurylist", inqurylist);
		model.addAttribute("id", id);
		return "thymeleaf/mypage/ask";
	}

	@RequestMapping("/mypage/suggest") // 오류문항 read
	public String suggest(HttpServletRequest req, Model model, String id) {
		MemberDTO member = util.Utils.getSessionUser(req);
		if (member == null) {
			return "redirect:/login.do";
		}

		String member_id = member.getMember_id();
		List<ProblemInquiryDTO> pilist = service.probleminquryread(member_id);
		model.addAttribute("pilist", pilist);
		model.addAttribute("id", id);
		return "thymeleaf/mypage/suggest";
	}

	@RequestMapping("/mypage/otoWrite") // 1:1문의사항 작성 페이지
	public String otoWrite(HttpServletRequest req, Model model) {
		MemberDTO member = util.Utils.getSessionUser(req);
		String member_id = member.getMember_id();
		model.addAttribute("member_id", member_id);
		return "thymeleaf/mypage/otoWrite";
	}

	@RequestMapping("/mypage/update") // 1:1문의사항 업데이트 페이지
	public String otoupdatepage(String inqury_id, Model model) {
		InquryDTO oto = service.inqurydetail(inqury_id);
		List<InquryFileDTO> inquryfile = service.fileread(inqury_id);
		model.addAttribute("oto", oto);
		model.addAttribute("inquryfile", inquryfile);
		return "thymeleaf/mypage/otoUpdate";
	}

	@RequestMapping("/mypage/oto/insert") // 1:1문의사항 insert
	public String otoinsert(InquryDTO inqurydto, RedirectAttributes redirect, HttpServletRequest req)
			throws IllegalStateException, IOException {
		MemberDTO member = util.Utils.getSessionUser(req);
		if (member == null) {
			return "redirect:/login.do";
		}

		String member_id = member.getMember_id();
		List<MultipartFile> files = inqurydto.getFiles();
		List<InquryFileDTO> inquryfiledtolist = fileservice.uploadFiles(files);
		service.inquryinsert(inqurydto, inquryfiledtolist);
		redirect.addAttribute("member_id", member_id);
		redirect.addAttribute("inqury_category", "1:1문의");
		return "redirect:/mypage/ask";
	}

	@RequestMapping("/mypage/oto/delete") // 1:1문의사항 삭제
	public String otodelte(String inqury_id, RedirectAttributes redirect, HttpServletRequest req) {
		MemberDTO member = util.Utils.getSessionUser(req);
		if (member == null) {
			return "redirect:/login.do";
		}

		String member_id = member.getMember_id();
		service.filedelete(inqury_id);
		service.inqurydelete(inqury_id);
		redirect.addAttribute("member_id", member_id);
		redirect.addAttribute("inqury_category", "1:1문의");
		return "redirect:/mypage/ask";
	}

	@Transactional
	@RequestMapping("/mypage/oto/update") // 1:1문의사항 업데이트
	public String otoupdate(InquryDTO inqurydto, RedirectAttributes redirect, HttpServletRequest req,
			String inqury_file_id) throws IllegalStateException, IOException {
		MemberDTO member = util.Utils.getSessionUser(req);
		if (member == null) {
			return "redirect:/login.do";
		}

		String member_id = member.getMember_id();
		List<MultipartFile> files = inqurydto.getFiles();
		List<InquryFileDTO> inquryfiledtolist = fileservice.uploadFiles(files);
		inquryfiledtolist.get(0).setInqury_file_id(inqury_file_id);
		service.inquryupdate(inqurydto);
		service.fileupdate(inquryfiledtolist);
		redirect.addAttribute("member_id", member_id);
		redirect.addAttribute("inqury_category", "1:1문의");
		return "redirect:/mypage/ask";
	}

	@RequestMapping("/mypage/oto2") // 1:1문의내역 자세히보기, 답변
	public String oto2(String inqury_id, Model model) {
		InquryDTO oto = service.inqurydetail(inqury_id);
		InquryReplyDTO inquryreply = service.inquryreply(inqury_id);
		List<InquryFileDTO> inquryfile = service.fileread(inqury_id);
		System.out.println("첨부파일read=>" + inquryfile);
		model.addAttribute("oto", oto);
		model.addAttribute("inquryreply", inquryreply);
		model.addAttribute("inquryfile", inquryfile);
		return "thymeleaf/mypage/oto";
	}

	@RequestMapping("mypage/filedown/{inqury_id}/{inqury_file_id}") // 파일다운로드
	public ResponseEntity<UrlResource> filedown(@PathVariable String inqury_id, @PathVariable String inqury_file_id)
			throws MalformedURLException {
		System.out.println("매개변수:" + inqury_id + inqury_file_id);
		InquryFileDTO file = service.filedown(new InquryFileDTO(inqury_file_id, inqury_id, "", ""));
		System.out.println("받아온 파일디티오=>" + file);
		UrlResource resource = new UrlResource("file:" + fileservice.getUploadpath(file.getInqury_store()));
		String encodedFilename = UriUtils.encode(file.getInqury_origin(), "UTF-8");
		String mycontenttype = "attachment;filename=\"" + encodedFilename + "\"";
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, mycontenttype).body(resource);
	}

	@RequestMapping("/mypage/wronganser") // 오답신고 자세히보기,답변
	public String wronganser(String inqury_id, Model model) {
		InquryDTO wronganser = service.inqurydetail(inqury_id);
		InquryReplyDTO inquryreply = service.inquryreply(inqury_id);
		model.addAttribute("wronganser", wronganser);
		model.addAttribute("inquryreply", inquryreply);
		return "thymeleaf/mypage/wronganser";
	}
}
