package com.multi.quizwiki.qboard.dto;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Alias("File")
public class FileResponse {
	
		private Long id;                      // 파일 번호 (PK)
	    private Long qboard_Id;                  // 게시글 번호 (FK)
	    private String original_Name;          // 원본 파일명
	    private String save_Name;              // 저장 파일명
	    private long size;                    // 파일 크기
	    private Boolean delete_Yn;             // 삭제 여부
	    private LocalDateTime regDate;    // 생성일시
	    private LocalDateTime deleted_Date;    // 삭제일시
}
