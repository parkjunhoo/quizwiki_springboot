package com.multi.quizwiki.qboard.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.multi.quizwiki.qboard.dao.FileMapper;
import com.multi.quizwiki.qboard.dto.FileRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
	
	private FileMapper filemapper;
	
	public void saveFiles(Long qboard_Id, List<FileRequest> files) {
		if(CollectionUtils.isEmpty(files)) {
			return;
		}
		for (FileRequest file: files) {
			file.setQboardId(qboard_Id);
		}
		filemapper.saveAll(files);
	}
}
