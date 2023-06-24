package com.multi.quizwiki.qboard.service;

import java.util.List;

import com.multi.quizwiki.qboard.dto.FileRequest;

public interface FileService {
	void  saveFiles(List<FileRequest> files);

}
