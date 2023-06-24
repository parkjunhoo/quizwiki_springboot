package com.multi.quizwiki.solv.dao;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.quizwiki.dto.SolvDTO;

import lombok.NoArgsConstructor;
import util.Utils;

@Repository
@NoArgsConstructor
public class SolvDAOImpl implements SolvDAO{

	SqlSession ss;
	
	@Autowired
	public SolvDAOImpl(SqlSession ss) {
		this.ss = ss;
	}


	
	@Override
	public int findCountByFilter(String memberId, Integer problemCateId, Boolean solvRight, Date minDate,
			Date maxDate) {
		
		Map<String,Object> paramMap = makeParamMap(memberId,problemCateId,solvRight,minDate,maxDate);
		
		
		return ss.selectOne("com.multi.quizwiki.solv.selectSolvCountByFilter",paramMap);
	}
	
	
	/*
	 * 아이디별로 문제 유형별로 틀렸는지 맞았는지 여부에 따라 푼 문제에 대한 정보를 받아 올 수 있습니다.
	 * 
	 * 
	 */
	@Override
	public List<SolvDTO> findByFilter(String memberId, Integer problemCateId, Boolean solvRight, Date minDate , Date maxDate, int size , int page) {
		
		Map<String,Object> paramMap = makeParamMap(memberId,problemCateId,solvRight,minDate,maxDate);
		
		Utils.addPageParam(paramMap, size , page);
		
		return ss.selectList("com.multi.quizwiki.solv.selectSolvByFilter",paramMap);
	}
	
	
	
	/***
	 * sql맵퍼에 넘길 파라미터를 만드는 작업 (중복되어 코드가 들어가서 따로 빼놨어용 한곳에서만 수정하면 적용되게)
	 * @param 파라미터 정보들
	 * @return 파라미터 정보를 담은 Map을 리턴
	 */
	private Map<String,Object> makeParamMap(String memberId, Integer problemCateId, Boolean solvRight, Date minDate,
			Date maxDate){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("memberId",memberId);
		paramMap.put("categoryId", problemCateId);
		paramMap.put("solvRight", solvRight);
		paramMap.put("minDate", minDate);
		paramMap.put("maxDate", maxDate);
		
		return paramMap;
	}
}
