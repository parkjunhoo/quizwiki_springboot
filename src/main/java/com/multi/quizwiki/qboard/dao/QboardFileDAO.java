package com.multi.quizwiki.qboard.dao;

import java.util.List;

import com.multi.quizwiki.qboard.dto.FileRequest;

public interface QboardFileDAO  {

	void  saveFiles(List<FileRequest> files);
}
