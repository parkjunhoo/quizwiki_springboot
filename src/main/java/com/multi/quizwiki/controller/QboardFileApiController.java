package com.multi.quizwiki.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multi.quizwiki.qboard.dto.FileResponse;
import com.multi.quizwiki.qboard.service.FileService;

import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
public class QboardFileApiController {
	private final FileService fileservice;
	
	
	//파일 리스트 조회
	@GetMapping("/qboard/{qboard_id}files") 
	public List<FileResponse> findAllQboardId(@PathVariable Long qboard_id) {
		return fileservice.findAllByQboardId(qboard_id);
	}
	
}
