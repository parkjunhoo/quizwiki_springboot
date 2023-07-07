package com.multi.quizwiki.qboard.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.quizwiki.pboard.entity.PboardEntity;
import com.multi.quizwiki.qboard.dto.QboardDTO;
import com.multi.quizwiki.qboard.dto.SearchDto;
import com.multi.quizwiki.qboard.entity.QboardEntity;
import com.multi.quizwiki.qboard.repository.QboardRepository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QboardDAOImpl implements QboardDAO {
	
	private SqlSession template;
	private QboardRepository repo;
	
	@Autowired
	public QboardDAOImpl(SqlSession template , QboardRepository repo) {
		this.template = template;
		this.repo = repo;
	}
	
	
	
	

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

	@Override
	public void increaseViewCount(Long qboard_id) {
			template.update("com.multi.quizwiki.qboard.increaseViewCount",qboard_id);
	}

	@Override
	public List<QboardDTO> findByCategory(String category) {
		return template.selectList("com.multi.quizwiki.qboard.findByCategory",category);
	}

	@Override
	public List<QboardDTO> findBySubject(String subject) {
		return template.selectList("com.multi.quizwiki.qboard.findBySubject",subject);
	}
		
		public List<QboardEntity> findTop10ByDeleteYnNotOrderByViewCountDesc() {
		return repo.findTop10ByDeleteYnNotOrderByViewCountDesc(1);
	}

	

	

}
