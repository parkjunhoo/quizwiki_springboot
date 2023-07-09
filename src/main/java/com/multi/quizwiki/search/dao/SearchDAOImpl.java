package com.multi.quizwiki.search.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.quizwiki.dto.ProblemSearchDTO;
import com.multi.quizwiki.dto.ProblemSearchParameterDTO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Repository
public class SearchDAOImpl implements SearchDAO{
	
	private SqlSession ss;
	
	@Autowired
	public SearchDAOImpl(SqlSession ss) {
		this.ss = ss;
	}

	@Override
	public List<ProblemSearchDTO> searchProblem(ProblemSearchParameterDTO param) {
		return ss.selectList("com.multi.quizwiki.search.findProblem",param);
	}

	@Override
	public int searchProblemCount(ProblemSearchParameterDTO param) {
		return ss.selectOne("com.multi.quizwiki.search.findProblemCount",param);
	}
	
	

}
