package com.sts.employeems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * JIRA ID=12358
 * 
 * @author Sri
 * @comment It is entry point to application
 */

@SpringBootApplication
@EnableScheduling
public class EmployeeMicroserviceApplication {

	static Logger logger = LoggerFactory.getLogger(EmployeeMicroserviceApplication.class);

	/**
	 * @author Sri
	 * @param args
	 * @comment It is entry point to application
	 */
	public static void main(String[] args) {
		SpringApplication.run(EmployeeMicroserviceApplication.class, args);
		logger.info("Started running..");
	}

}
