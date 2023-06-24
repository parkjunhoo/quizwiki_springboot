package com.multi.quizwiki.qboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.multi.quizwiki.qboard.dao.FileMapper;
import com.multi.quizwiki.qboard.dto.FileRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl {
	
	private final FileMapper filemapper;

	
	public void saveFiles(Long qboard_Id, List<FileRequest> files) {
		if(CollectionUtils.isEmpty(files)) {
			return;
			
		}
		for (FileRequest file: files) {
			file.setQboardId(qboard_Id);
			log.info("파일 실행");
		}
		filemapper.saveAll(files);
	}

	
}
