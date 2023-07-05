package com.multi.quizwiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Controller
@RequestMapping("note")
public class NoteController {
	
	@GetMapping("/list")
	public String show_list() {
		return "thymeleaf/note/note_list";
	}
	
	@GetMapping("/mylist")
	public String show_mylist() {
		return "thymeleaf/note/note_mylist";
	}
	
	@GetMapping("/read")
	public String show_read() {
		return "thymeleaf/note/note_read_popup";
	}
	
	@GetMapping("/write")
	public String show_write() {
		return "thymeleaf/note/note_write_popup";
	}
}
