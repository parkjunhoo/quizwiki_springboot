package com.multi.quizwiki.qboard.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class FileResponse {
	
	 private Long id;                                          // PK
	    private String title;                                     // 제목
	    private String content;                                   // 내용
	    private String member_id;                                    // 작성자
	    private Boolean noticeYn;                                 // 공지글 여부
	    private List<MultipartFile> files = new ArrayList<>();    // 첨부파일 List

}
