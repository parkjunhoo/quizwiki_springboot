package com.multi.quizwiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@RequestMapping("popup")
@Controller
public class PopupController {
	
	@GetMapping("/upload/image")
	public String show_image_upload() {
		
		return "thymeleaf/popup/image_upload_popup";
	}
}
