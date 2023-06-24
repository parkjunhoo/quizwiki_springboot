package com.multi.quizwiki.qboard.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.quizwiki.qboard.dto.FileRequest;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class QboardFileDAOImpl implements QboardFileDAO {
	@Autowired
	private final SqlSession template;
	
	
	
	@Override
	public void saveFiles(List<FileRequest> files) {
			template.insert("com.multi.quizwiki.qboard_file.saveAll",files);
	}

}
