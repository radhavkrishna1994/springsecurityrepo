package com.training.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
	
	//authentication
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		log.info("I am in Authentication");
			
		
	}
	
	//authorization
	public void configure(HttpSecurity http) throws Exception
	{
		log.info("I am in Authorization");
		http
		.authorizeRequests()
		.antMatchers("/admin/**")
		.hasRole("ADMIN_ROLE")
		.antMatchers("/user/**")
		.hasRole("USER_ROLE")
		.and()
		.formLogin();
		
	}
	
	
}
