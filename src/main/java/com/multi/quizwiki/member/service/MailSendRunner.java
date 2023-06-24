package com.multi.quizwiki.member.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailSendRunner implements ApplicationRunner{

	private final JavaMailSender mailSender = null;
	
	@Value("${spring.mail.username}")
    private String from;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		 MimeMessage msg = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(msg,"UTF-8");
	        helper.setFrom("hyen1616@naver.com");
	        helper.setTo("skar_u@naver.com");
	        helper.setSubject("테스트메일");
	        helper.setText("메일테스트");
	        mailSender.send(msg);
	}


}
