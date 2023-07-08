package com.multi.quizwiki.manager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.quizwiki.manager.dto.RecoProblemDTO;
import com.multi.quizwiki.manager.dto.SolvCountDTO;

@Repository
public class ManagerDAOImpl implements ManagerDAO{
	
	private SqlSession ss;
	
	@Autowired
	public ManagerDAOImpl(SqlSession ss) {
		this.ss = ss;
	}

	@Override
	public List<SolvCountDTO> findSolvCountBySubjectName(String memberId) {
		return ss.selectList("com.multi.quizwiki.manager.findSolvCountBySubjectName" , memberId);
	}

	@Override
	public List<SolvCountDTO> findSolvCountByProblemCateName(String memberId) {
		return ss.selectList("com.multi.quizwiki.manager.findSolvCountByProblemCateName" , memberId);
	}

	@Override
	public List<RecoProblemDTO> recommandProblem(String memberId, boolean distinct, int limit) {
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("distinct", distinct);
		params.put("limit", limit);
		return ss.selectList("com.multi.quizwiki.manager.recommandProblem",params);
	}

}
