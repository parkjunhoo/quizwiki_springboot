package com.multi.quizwiki.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
public class EmailService {

	private JavaMailSender javaMailSender;

	@Autowired
	public EmailService(JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}
	
	public void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("skar_u@naver.com");
		message.setSubject("테스트메일이용");
		message.setText("메일테스트임요 ㅎㅇ");
		javaMailSender.send(message);
	}
	
	
}
