package com.sts.employeems.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.sts.employeems.utill.Encrypt_Deprypt_Util;

@Configuration
public class MailConfig {

	@Autowired
	private Encrypt_Deprypt_Util encDeprUtil;

	@Bean
	public JavaMailSender javaMailSender() throws Exception {

		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com");
		javaMailSender.setPort(587);

		javaMailSender.setUsername("ambatisri4@gmail.com");

		javaMailSender.setPassword(encDeprUtil.decrypt("Ez8Dwi66BsO6Xn9ctwYo8Q=="));// AmbatiSri@123

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.starttls.enable", "true");

		javaMailSender.setJavaMailProperties(javaMailProperties);

		return javaMailSender;
	}
}
