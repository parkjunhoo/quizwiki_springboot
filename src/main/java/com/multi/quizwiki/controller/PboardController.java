package com.multi.quizwiki.controller;

import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.quizwiki.common.FileUploadLogicService;
import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.pboard.entity.PboardEntity;
import com.multi.quizwiki.pboard.entity.PboardLikeEntity;
import com.multi.quizwiki.pboard.entity.PboardReplyEntity;
import com.multi.quizwiki.pboard.service.PboardService;
import com.multi.quizwiki.problem.entity.PrintFileEntity;
import com.multi.quizwiki.problem.entity.ProblemEntity;
import com.multi.quizwiki.problem.entity.ProblemInquiryEntity;
import com.multi.quizwiki.problem.entity.ProblemLikeEntity;
import com.multi.quizwiki.solv.entity.SolvEntity;

import util.Utils;


@Controller
@RequestMapping("pboard")
public class PboardController {
	
	PboardService pboardService;
	FileUploadLogicService fileUpload;
	
	public PboardController() {
		
	}
	
	@Autowired
	public PboardController(PboardService pboardService , FileUploadLogicService fileUpload) {
		this.pboardService = pboardService;
		this.fileUpload = fileUpload;
	}

	@GetMapping("/list")
	public String show_pboard_list(Model model, 
			int cate,
			@RequestParam(defaultValue = "1") int page, // 현재 페이지를 받아옴(사용자 컨트롤을 만들때를 대비해서 파라미터에서 뽑음)
			@RequestParam(defaultValue = "20") int size, // 한 페이지에 보여줄 size를 받아옴(사용자 컨트롤을 만들때를 대비해서 파라미터에서 뽑음)
			
			@RequestParam(value="stype", required = false) String stype, //stype=title , id (제목으로 검색 , id로 검색)
			@RequestParam(value="keyword", required = false) String keyword // 검색 keyword
			) {
		
		//JPA를 활용한 페이징처리 받아온 파라미터를 활용하여 날짜순으로 정렬하는  pageRequest객체를 생성
		PageRequest pageReq = PageRequest.of(page-1, size, Sort.by("pboardRegDate").descending());
		
		
		//Page객체 선언
		Page<PboardEntity> pboardPage = null;
		
		
		if(stype != null && keyword != null) { //검색조건과 검색어가 있을시
			if(stype.equals("title")) {//제목으로 검색시
				pboardPage = pboardService.pboard_findByCateTitleContaining(cate, keyword, pageReq);
			}else if(stype.equals("id")){//id로 검색시
				pboardPage = pboardService.pboard_findByCateMemberIdContaining(cate, keyword, pageReq);
			}
			
		}else {//아무 검색을 실행하지 않았을시
			pboardPage = pboardService.pboard_findByCate(cate, pageReq);
		}
		
		//전체 페이지숫자를 받아온다
		int totalPage = pboardPage.getTotalPages();
		
		//모델에 Util함수를 통해 pagingSeq(페이지 목록 int List)를 생성
		model.addAttribute("pages",util.Utils.makePagingSeq(page, 5, totalPage));
		
		//currnetPage 정보를 담는다 (담는 이유: 프론트단에서 page다음버튼 이전버튼 활성화 비활성화하려고)
		model.addAttribute("currentPage",page);
		
		//전체 페이지 숫자를 담는다 (담는 이유: 프론트단에서 page다음버튼 이전버튼 활성화 비활성화하려고)
		model.addAttribute("totalPage",totalPage);
		
		//게시물 정보가 담긴 객체를 담는다
		model.addAttribute("pboardList",pboardPage);
		
		//오늘 날짜가 담긴 객체를 담는다 (담는 이유: 오늘 올라온 게시물은 시간만표시 하게 할라공,, )
		Timestamp today = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
		model.addAttribute("today",today);
		
		return "thymeleaf/pboard/pboard_list";
	}
	
	
	@GetMapping("/write")
	public String show_pboard_write(Model model, HttpServletRequest req) {
		
		if(!Utils.loginCheck(req)) { //로그인을 체크한뒤 로그인 되어 있지않으면 로그인창으로 넘긴다
			return "redirect:/login.do";
		}
		
		//왼쪽에 카테고리에 들어갈 정보를 담는다ㅋㅋ 사실 인터셉터로 처리해야할거같은데 귀찮아서 그냥 여기에 넣엇다
		model.addAttribute("problemCateList",pboardService.problemCate_findAll());
		
		//front단에서 해당 어트리뷰트를 판단해 게시글 작성인지 수정인지 판단한다.
		//(담는이유 : 프론트를 재활용하고 있기 때문에 알려줘야한다,,)
		model.addAttribute("state","w");
		return "thymeleaf/pboard/pboard_write";
	}
	
	//write 메소드안에서 파라미터값으로 분기처리해도 되지만 그냥 따로 만듬
	@GetMapping("/edit")
	public String show_pboard_edit(Model model, int no , int cate, HttpServletRequest req) {
		//no에 pboardId를 넘겨받고 해당하는 게시물을 찾아 pboard에 담는다.
		PboardEntity pboard = pboardService.pboard_findById(no);
		
		//user에 로그인된 유저 객체를 받는다.
		MemberDTO user = Utils.getSessionUser(req);
		
		//로그인 되어있고, 게시물 작성자가 로그인 유저인지 판단한다.
		if(user != null && user.getMember_id() == pboard.getMemberId()) {//로그인되있고, 게시물작성자가 맞다면
			model.addAttribute("problemCateList",pboardService.problemCate_findAll());
			model.addAttribute("pboard",pboard);
			model.addAttribute("state","e");
			
			//수정할수있도록 state를 e로 지정하고 작성페이지로 넘긴다.
			return "thymeleaf/pboard/pboard_write";
		}
		
		//로그인 되어있지않거나, 게시물작성자가 아니라면 그냥 읽기페이지로 넘긴다.
		return "redirect:/pboard/read?cate="+cate+"&no="+no;
	}
	
	@GetMapping("/read")
	public String show_pboard_read(Model model , int no) {
		
		//no로 pboardId를 받아 해당하는 게시물에 대한 정보를 model에 담는다.
		PboardEntity pboard = pboardService.pboard_findById(no);
		model.addAttribute("pboard",pboard);
		model.addAttribute("cate",pboard.getPboardCateId());
		
		//조회수를 증가시킨다.
		pboardService.incrementShowCount(no);
		
		return "thymeleaf/pboard/pboard_read";
	}
	
	@GetMapping("/cbt")
	public String show_pboard_cbt(int no , Model model, HttpServletRequest req) {
		
		//로그인이 안되어 있으면 로그인페이지로 redirect
		//로그인 없이도 가능하게 할지 일단 문제풀이는 로그인이 필요한 기능으로..
		if(!Utils.loginCheck(req)) {
			return "redirect:/login.do";
		}
		
		//pboard에 대한 정보가 cbt에서 필요하기 땜에 담는다.
		//엔티티에 problem에 대한 정보도 조인되어 list로 들고있기때문에 pboard엔티티객체에서도 접근할 수 잇다.
		PboardEntity pboard = pboardService.pboard_findById(no);
		model.addAttribute("pboard",pboard);
		
		return "thymeleaf/pboard/cbt";
	}
	 
	
	
	@PostMapping(value="/write")
	@ResponseBody
	public JsonNode pboard_write(
		@RequestPart(name= "sendData") String sendData, //파일이 아닌것들 jsonString으로 프론트에서 통째로 받아온다.
		@RequestPart(name = "printfile", required = false) List<MultipartFile> printfileList, //업로드한 파일 리스트 받아온다
		HttpServletRequest req) throws JsonMappingException, JsonProcessingException{
		
		//일단 성공못하면 다 false칠거기떄매 msg에 false로 초기화 ㅋㅋ
		String msg = "false";
		
		if(!Utils.loginCheck(req)) {//로그인 체크 
			//로그인 안되있으면 객체에는 null , 메시지는 notloggedin
			msg = "notlogin";
			return util.Utils.getJsonStringAsResForm(null, msg);
		}
		
		//밑에 만들어둔 PboardParseStructure의 생성자에서는 JsonString을 받아서
		//알아서 객체들로 변환해 필드멤버로 들고잇게해두엇다. (수정할때도 똑같은 로직이 들어가기떔 편의상 만듬..)
		PboardParseStructure pps = new PboardParseStructure(sendData);
		
		//pboard에 memberId를 로그인 되어있는 아이디로 바꿔치기한다.
		pps.pboard.setMemberId(Utils.getSessionUser(req).getMember_id());
		
		//만들어둔 insert로직에 pps의 정보들을 전달하여 게시물을 작성한다.
		PboardEntity p = pboardService.pboard_insert(pps.getPboard(), pps.getProblemList(), 
						pps.getProblemChoiseList() ,printfileList, pps.getPrintfileArr());
		
		//정상적으로 작성이 완료된것을 확인하기위해 null체크 , null이면 msg에 false가 전달될것이다.
		if(p != null) {
			msg = "true";
		}
		
		return util.Utils.getJsonStringAsResForm(p.getPboardId(), msg);
	}
	
	@PostMapping(value="/edit")
	@ResponseBody
	public int pboard_edit(
		@RequestPart(name= "sendData") String sendData, 
		@RequestPart(name = "printfile", required = false) List<MultipartFile> printfileList) throws JsonMappingException, JsonProcessingException{
		
		//todo write처럼 jsonnode를 반환하게 바꿀예정
		//todo 로그인된 유저와 작성자를 대조해서 예외처리 할 예정 (get에서 막고잇어서 정상적인 루트로는 여기로 들어올일은 없겠지만 여기서도 막아야된다)
		
		
		PboardParseStructure pps = new PboardParseStructure(sendData);
		
		pboardService.pboard_edit(pps.getPboard(), pps.getProblemList(), 
				pps.getProblemChoiseList() ,printfileList, pps.getPrintfileArr());
		
		return pps.getPboard().getPboardId();
		
		
	}
	
	
	
	
	@PostMapping(value="/reply/write")
	public String pboard_reply_write(PboardReplyEntity pboardReply , int cate, HttpServletRequest req) {

		MemberDTO user = Utils.getSessionUser(req);
		if(user == null) {
			//로그인 안되어있으면 로그인.do로
			return "redirect:/login.do";
		}
		
		pboardReply.setMemberId(user.getMember_id());
		pboardService.reply_insert(pboardReply);
		
		return "redirect:/pboard/read?no="+pboardReply.getPboardId()+"&cate="+cate;
	}
	
	
	@PostMapping(value="/get/pboard")
	@ResponseBody
	public PboardEntity pboard_get(int no) {
		//pboard정보를 id로 조회해오는 api
		return pboardService.pboard_findById(no);
	}
	
	
	
	//업로드된 이미지를 찾아와서 뿌린당. 요거 어떻게 맵핑이랑 파일경로 잘 처리하면 통합적으로 만들수잇을거같긴한디 일단...
	@RequestMapping("/printfile/find/{problemId}")
	public ResponseEntity<UrlResource> printfile_fild(@PathVariable int problemId) throws MalformedURLException{
		PrintFileEntity printfile = pboardService.printfile_findByProblemId(problemId);
		UrlResource resource = new UrlResource("file:"+fileUpload.getUploadpath("printfile",printfile.getPrintfileStore()));
		
		//파일을 다운로드하는게아닌 이미지자체를 뿌릴꺼기땜에 주석처리햇다..
		//String encoded = UriUtils.encode(printfile.getPrintfileOrigin(),"UTF-8");
		//String contentType = "attachment; filename=\"" + encoded +"\"";
		
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(resource);
	}
	
	
	@GetMapping(value="/get/problemlike")
	@ResponseBody
	public JsonNode problem_like_get(int problemId , HttpServletRequest req) throws JsonProcessingException {
		
		
		String msg = "false";
		
		MemberDTO user = Utils.getSessionUser(req);
		if(user == null) {
			return util.Utils.getJsonStringAsResForm(null, "notlogin");
		}
		
		String memberId = user.getMember_id();
		
		ProblemLikeEntity problemLike = pboardService.problemlike_findByMemberIdAndProblemId(memberId, problemId);
		if(problemLike != null) {
			msg = "true";
		}
		
		return util.Utils.getJsonStringAsResForm(problemLike, msg);
	}
	
	@PostMapping(value="/post/problemlike")
	@ResponseBody
	public JsonNode problem_like_post(int problemId , HttpSession session, HttpServletRequest req) throws JsonProcessingException {
		
		String msg = "false";
		
		MemberDTO user = Utils.getSessionUser(req);
		if(user == null) {
			return util.Utils.getJsonStringAsResForm(null, "notlogin");
		}
		
		String memberId = user.getMember_id();
		
		ProblemLikeEntity problemLike = pboardService.problemlike_insert(new ProblemLikeEntity(problemId, memberId));
		if(problemLike != null) {
			msg = "true";
		}
		return util.Utils.getJsonStringAsResForm(problemLike, msg);
	}
	
	@PostMapping(value="/post/solv")
	@ResponseBody
	public JsonNode solv_post(@RequestBody List<SolvEntity> solvList , HttpServletRequest req) {
		
		String msg = "false";
		
		MemberDTO user = Utils.getSessionUser(req);
		if(user == null) {
			return util.Utils.getJsonStringAsResForm(null, "notlogin");
		}
		
		String memberId = user.getMember_id();
		
		solvList.forEach((s)->{
			s.setMemberId(memberId);
		});
		
		List<SolvEntity> savedList = pboardService.solv_insertAll(solvList);
		
		if(savedList != null) {
			msg="true";
		}
		
		return util.Utils.getJsonStringAsResForm(savedList, msg);
	}
	
	@PostMapping(value="/post/probleminquiry")
	@ResponseBody
	public JsonNode problem_inquiry_post(int problemId ,String inquiryContent , HttpServletRequest req) throws JsonProcessingException {
		
		String msg = "false";
		
		MemberDTO user = Utils.getSessionUser(req);
		if(user == null) {
			return util.Utils.getJsonStringAsResForm(null, "notlogin");
		}
		
		String memberId = user.getMember_id();
		
		ProblemInquiryEntity problemInquiry = pboardService.inquiry_insert(new ProblemInquiryEntity(memberId, problemId, inquiryContent));
		
		if(problemInquiry != null) {
			msg = "true";
		}
		return util.Utils.getJsonStringAsResForm(problemInquiry, msg);
	}
	
	@PostMapping(value="/post/pboardlike")
	@ResponseBody
	public JsonNode pboard_like_post(int pboardId, HttpServletRequest req) throws JsonProcessingException {
		
		String msg = "false";
		
		MemberDTO user = Utils.getSessionUser(req);
		if(user == null) {
			return util.Utils.getJsonStringAsResForm(null, "notlogin");
		}
		
		String memberId = user.getMember_id();
		PboardLikeEntity pboardLike = null;
		
		PboardLikeEntity pl = pboardService.pboardlike_findByMemeberIdAndPboardId(memberId, pboardId);
		if(pl == null) {
			pboardLike = pboardService.pboardlike_insert(new PboardLikeEntity(memberId, pboardId));
			if(pboardLike != null) {
				msg = "true";
			}
		}else {
			msg = "duplicated";
		}
		
		
		return util.Utils.getJsonStringAsResForm(pboardLike, msg);
	}
	
	
	private class PboardParseStructure{
		PboardEntity pboard;
		List<ProblemEntity> problemList;
		List<List<String>> problemChoiseList;
		List<Integer> printfileArr;
		
		private PboardParseStructure(String sendData) throws JsonMappingException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,String> map = mapper.readValue(sendData, Map.class);
			this.pboard =  mapper.convertValue(map.get("pboard"), PboardEntity.class);
			this.problemList = mapper.convertValue(map.get("problem"), new TypeReference<List<ProblemEntity>>() {});
			this.problemChoiseList = mapper.convertValue(map.get("problemChoiseList"), new TypeReference<List<List<String>>>() {});
			this.printfileArr = mapper.convertValue(map.get("printfileArr"), new TypeReference<List<Integer>>() {});
		}
		
		private PboardEntity getPboard() {
			return this.pboard;
		}
		private List<ProblemEntity> getProblemList() {
			return this.problemList;
		}
		private List<List<String>> getProblemChoiseList() {
			return this.problemChoiseList;
		}
		private List<Integer> getPrintfileArr() {
			return this.printfileArr;
		}
	}
}
