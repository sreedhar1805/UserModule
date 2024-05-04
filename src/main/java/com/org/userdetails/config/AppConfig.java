package com.org.userdetails.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.org.userdetails.service.RoleService;
import com.org.userdetails.service.UserService;

import jakarta.annotation.PostConstruct;

@Configuration
public class AppConfig {
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		roleService.createDefaultRoles();
		userService.insertDefaultUser();
	}

	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
