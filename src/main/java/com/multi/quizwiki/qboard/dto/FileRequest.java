package com.multi.quizwiki.qboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileRequest {
		
	 private Long id;                // 파일 번호 (PK)
	 private Long qboard_Id;            // 게시글 번호 (FK)
	 private String original_Name;    // 원본 파일명
	 private String save_Name;        // 저장 파일명
	 private long size;              // 파일 크기
	
	 
	 @Builder
	    public FileRequest(String original_Name, String save_Name, long size) {
	        this.original_Name = original_Name;
	        this.save_Name = save_Name;
	        this.size = size;
	    }
	 
	   public void setQboardId(Long qboard_Id) {
	        this.qboard_Id = qboard_Id;
	        
	    }

}
