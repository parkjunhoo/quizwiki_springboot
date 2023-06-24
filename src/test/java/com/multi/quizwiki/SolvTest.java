package com.multi.quizwiki;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.multi.quizwiki.dto.SolvDTO;
import com.multi.quizwiki.solv.dao.SolvDAO;

import util.Utils;

@SpringBootTest
public class SolvTest {
	
	@Autowired
	SolvDAO dao;
	
	@Test
	public void findByFilter() {
		String memberId = "test";
		
		LocalDate ld = LocalDate.of(2023, 6, 11);
		Date minDate = Date.valueOf(ld);
		
		ld = LocalDate.of(2023, 6, 21);
		Date maxDate = Date.valueOf(ld);
		
		int count = dao.findCountByFilter(memberId, 0, null, null , null);
		int size = 5;
		int page = 1;
		int totalPage = Utils.getTotalPage(count, size);
		
		List<Integer> pages = Utils.makePagingSeq(page, size, totalPage);
		
		List<SolvDTO> result = dao.findByFilter(memberId, 0, null, null, null,size,page);
		
		System.out.println("view에 뿌려질 페이지 버튼"+pages);
		System.out.println("총 레코드 갯수"+count);
		System.out.println("전체 페이지수"+totalPage);
		System.out.println("dto list 사이즈"+result.size());
		result.forEach((res)->{
			System.out.println(res);
		});
	}
	
}
