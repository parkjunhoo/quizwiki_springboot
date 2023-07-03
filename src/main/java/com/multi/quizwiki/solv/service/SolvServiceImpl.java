package com.multi.quizwiki.solv.service;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.quizwiki.dto.SolvDTO;
import com.multi.quizwiki.solv.dao.SolvDAO;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@Transactional
public class SolvServiceImpl implements SolvService{

	private SolvDAO dao;
	
	@Autowired
	public SolvServiceImpl(SolvDAO dao) {
		this.dao = dao;
	}

	@Override
	public int findCountByFilter(String memberId, Integer problemCateId, Boolean solvRight, Date minDate,
			Date maxDate) {
		return dao.findCountByFilter(memberId, problemCateId, solvRight, minDate, maxDate);
	}

	@Override
	public List<SolvDTO> findByFilter(String memberId, Integer problemCateId, Boolean solvRight, Date minDate,
			Date maxDate, int size, int page) {
		System.out.println(problemCateId+"서비스"+solvRight+memberId+minDate+maxDate);
		System.out.println("서비스"+dao.findByFilter(memberId, problemCateId, solvRight, minDate, maxDate, size, page));
		return dao.findByFilter(memberId, problemCateId, solvRight, minDate, maxDate, size, page);
	}
	

}
