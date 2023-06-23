package com.multi.quizwiki.qboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.multi.quizwiki.qboard.dto.FileRequest;

@Mapper
public interface FileMapper {
	   
	//파일 정보 저장
	void saveAll(List<FileRequest> files);
}
