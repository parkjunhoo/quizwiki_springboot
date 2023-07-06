package com.multi.quizwiki;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.quizwiki.qboard.dto.QboardDTO;
import com.multi.quizwiki.qboard.service.QboardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@SpringBootTest
@Slf4j
public class QboardControllerTest {
	@Autowired
	QboardService qboardservice;
	@Test
	public void test() {
		log.info("hu");
	}
	
}
