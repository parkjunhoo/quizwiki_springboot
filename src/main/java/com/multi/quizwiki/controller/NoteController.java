package com.multi.quizwiki.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.quizwiki.dto.MemberDTO;
import com.multi.quizwiki.note.entity.NoteEntity;
import com.multi.quizwiki.note.entity.NotePageEntity;
import com.multi.quizwiki.note.service.NoteService;

import lombok.NoArgsConstructor;
import util.Utils;

@NoArgsConstructor
@Controller
@RequestMapping("note")
public class NoteController {
	
	private NoteService service;
	
	@Autowired
	public NoteController(NoteService service) {
		this.service = service;
	}
	
	@GetMapping("/list")
	public String show_list() {
		
		return "thymeleaf/note/note_list";
	}
	
	@GetMapping("/mylist")
	public String show_mylist(Model model, HttpServletRequest req,
			@RequestParam(defaultValue = "1") int page) {
		
		MemberDTO member = util.Utils.getSessionUser(req);
		
		int size = 6;
		
		if(member == null) {
			return "redirect:/login.do";
		}
		
		String memberId = member.getMember_id();
		
		PageRequest pageReq = PageRequest.of(page-1, size, Sort.by("noteRegdate").descending());
		
		
		Page<NoteEntity> noteList = service.findByMemberId(memberId, pageReq);
		int totalPage = noteList.getTotalPages();
		
		model.addAttribute("noteList",noteList);
		model.addAttribute("pages",util.Utils.makePagingSeq(page, 5, totalPage));
		model.addAttribute("currentPage",page);
		model.addAttribute("totalPage",totalPage);
		Timestamp today = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
		model.addAttribute("today",today);
		return "thymeleaf/note/note_mylist";
	}
	
	@GetMapping("/read/{noteId}")
	public String show_read(Model model, @PathVariable int noteId) {
		model.addAttribute("noteId",noteId);
		return "thymeleaf/note/note_read_popup";
	}
	
	@PostMapping("/read/{noteId}")
	@ResponseBody
	public NoteEntity find_note(@PathVariable int noteId) {
		NoteEntity note = service.note_findById(noteId);
		return note;
	}
	
	@GetMapping("/write")
	public String show_write(HttpServletRequest req) {
		if(!Utils.loginCheck(req)) {//로그인 체크 
			return "redirect:/login.do";
		}
		return "thymeleaf/note/note_write_popup";
	}
	
	
	@PostMapping("/write")
	@ResponseBody
	public String note_write(@RequestBody String sendData , HttpServletRequest req) throws JsonMappingException, JsonProcessingException {
		
		String msg = "false";
		
		if(!Utils.loginCheck(req)) {//로그인 체크 
			return "notlogin";
		}
		
		MemberDTO member = util.Utils.getSessionUser(req);
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,String> map = mapper.readValue(sendData, Map.class);
		NoteEntity note =  mapper.convertValue(map.get("note"), NoteEntity.class);
		List<NotePageEntity> notePageList = mapper.convertValue(map.get("notepage"), new TypeReference<List<NotePageEntity>>() {});
		note.setMemberId(member.getMember_id());
		note.setNotePrivate(false);
		note.setNoteShowCount(0);
		
		NoteEntity res = service.note_insert(note, notePageList);
		
		
		return Integer.toString(res.getNoteId());
	}
}
