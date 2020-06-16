package com.sts.employeems.schedule;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.sts.employeems.entity.Employee;
import com.sts.employeems.repository.EmployeeRepo;

@Component
public class MailScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailScheduler.class);

	private static final String BR = "</b><br>";
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmployeeRepo employeeRepo;

	//@Scheduled(fixedDelay = (10 * 60 * 1000)) // ms
	public void mailSender() {

		StringBuilder text = null;

		List<Employee> nonActiveEmployeeList = null;

		try {

			LOGGER.info("Start of mailSender()");
			// logic mon @10AM
			// database call (false) -pool
			// mail to HR

			text = new StringBuilder();

			nonActiveEmployeeList = employeeRepo.findByIsActive("false");

			if (!CollectionUtils.isEmpty(nonActiveEmployeeList)) {

				for (Employee employee : nonActiveEmployeeList) {

					text.append("<h2>EmpId:<b style='color:red';>").append(employee.getEmpId()).append(BR)
							.append("Name:<b style='color:red';>").append(employee.getName()).append(BR)
							.append("Experience:<b style='color:red';>").append(employee.getExperience())
							.append(" years").append(BR).append("Designation:<b style='color:red';>")
							.append(employee.getDesignation()).append(BR).append("Skills:<b style='color:blue';>")
							.append(employee.getSkills()).append("</b></h2><br>")
							.append("-----------------------------------------------------<br>");

				}

			} else {
				LOGGER.info("Non active employees are not found");
				text.append("Non active employees are not found");
			}

			final String finalText = text.toString();
			mailSender.send(new MimeMessagePreparator() {

				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {

					MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, true);

					mailMessage.setSubject("Employee Info");
					mailMessage.setTo("-----@gmail.com");
					mailMessage.setText(finalText, true);

				}
			});

			LOGGER.info("End of mailSender()");

		} catch (Exception e) {
			LOGGER.error("Error while sending the mail", e);

		}
	}
}
