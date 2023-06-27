package com.multi.quizwiki.qboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.multi.quizwiki.qboard.dto.FileRequest;
import com.multi.quizwiki.qboard.dto.FileResponse;

@Mapper
public interface FileMapper {
	   
	//파일 정보 저장
	void saveAll(List<FileRequest> files);
	//파일 리스트 조회
	List<FileResponse> findAllByQboardId(Long qboard_id);
	//파일 리스트 조회
	List<FileResponse> findAllByIds(List<Long> ids); 
	//파일 삭제
	void deleteAllByIds(List<Long> ids);
	//파일  상세정보 조회
	
	FileResponse findById(Long id);
	
	
		
}
