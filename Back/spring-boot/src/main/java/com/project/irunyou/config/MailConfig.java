package com.project.irunyou.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

// 홍지혜

@Configuration
// 설정파일을 만들기 위한 어노테이션, Bean을 등록하기 위한 어노테이션
// @Bean 어노테이션이 동봉된 메소드를 서넝ㄴ하면, 그 메소드를 통해 빈을 정의하고 생명주기를 설정
public class MailConfig {
	
	@Bean
	public JavaMailSender MailService() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost("smtp.gamil.com");	// smtp 서버 주소
//		mailSender.setPort(857); // 메일 인증 서버 포트
		mailSender.setUsername("projectirunyou@gmail.com");	// 메일 전송 계정 아이디
		mailSender.setPassword("rspfkuzebsquojwb");	
	
		mailSender.setJavaMailProperties(getMailProperties());	// 메일 인증 서버 정보 설정하기
		
		return mailSender;
	}
	
	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol","smtp");	// 프로토콜 설정
		properties.setProperty("mail.smtp.auth", "true");	// stmp인증
		properties.setProperty("mail.smtp.starttls.enable","true");	//	smtp starttles 사용
		properties.setProperty("mail.smtp.debug", "true");	// 디버그 사용
		properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");	// ssl인증 서버는 smtp.gmail.com
		properties.setProperty("mail.smtp.ssl.enable", "true"); // ssl 사용
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		
		return properties;
	}
}
