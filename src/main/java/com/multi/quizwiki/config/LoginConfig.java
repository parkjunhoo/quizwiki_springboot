package com.multi.quizwiki.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.multi.quizwiki.interceptor.LoginCheckInterceptor;


@Configuration
public class LoginConfig implements WebMvcConfigurer{
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCheckInterceptor())
		.order(1)
		.addPathPatterns("/**") //모든 것에 대해 로그인 가능하도록
		.excludePathPatterns("/main","/login.do","/signupType","/findId.do","/findPass.do",
				"/findId","/findPass","/signupAgreement1","/signup1","/signup2","/signupAgreement2",
				"/faqt","/images/**","/css/**","/font/**","/js/**","/libs/**","/pboard/**","/qboard/**","/kakao/**",
				"/idChk","/login/mailConfirm","/signup","/note/**","/popup/**", "/check/sendSMS", "/sms", "/memberPhoneCheck",
				"note/**","/get/logincheck","/search/problem/**","/post/login");
	}
}