package com.sts.employeems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.sts.employeems.resource"))
				.paths(PathSelectors.ant("/employee/**")).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {

		return new ApiInfoBuilder().title("Employee REST API Documentation").version("1.0.0")
				.description("\"Employee microservice endpoint documentation\"")
				.contact(new Contact("John", "www.sriTechSolutions.com", "john.123@sritectsolutions.com"))
				.license("sriTechSolutions").licenseUrl("www.sriTechSolutions.com").build();

	}
}
