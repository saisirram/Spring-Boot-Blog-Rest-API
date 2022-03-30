package com.sai.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	private ApiKey apiKey()
	{
		return new ApiKey("JWT", AUTHORIZATION_HEADER,"header");
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Spring Boot Blog REST APIs", "Spring Boot Blog REST API Documentation", "1",
				"Terms of Services", new Contact("Sai Sriram", "www.saisriram.com", "saisriram@gmail.com"),
				"License of API", "API License URl", Collections.emptyList());
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()))
				.select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
	
	private SecurityContext securityContext()
	{
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}
	
	private List<SecurityReference> defaultAuth()
	{
		AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}

}
