package com.multi.quizwiki.qboard.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.quizwiki.qboard.dto.QboardDTO;
import com.multi.quizwiki.qboard.dto.SearchDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QboardDAOImpl implements QboardDAO {
	@Autowired
	SqlSession template;
	
	

	@Override
	public List<QboardDTO> getBoardList(SearchDto params) {
		return template.selectList("com.multi.quizwiki.qboard.selectqboardlist",params);
	}

	@Override
	public QboardDTO getQboardDetail(Long qboard_id) {
		return template.selectOne("com.multi.quizwiki.qboard.findById", qboard_id);
	}

	@Override
	public int update(QboardDTO qboard) {
			return template.update("com.multi.quizwiki.qboard.update",qboard);
	}

	

	@Override
	public int deleteQboard(Long qboard_id) {
		log.info("dao실행");
		 return template.delete("com.multi.quizwiki.qboard.deleteById",qboard_id);
		
		
	}

	@Override
	public int count(SearchDto params) {
			
		return template.selectOne("com.multi.quizwiki.qboard.count",params);
	}

	@Override
	public Long save(QboardDTO qboard) {
			template.insert("com.multi.quizwiki.qboard.insert",qboard);
		return qboard.getQboard_id();	
	}

	

	

}
