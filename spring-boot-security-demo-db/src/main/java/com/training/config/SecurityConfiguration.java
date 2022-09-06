package com.training.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.training.services.MyUserDetailsService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
	
	//authentication
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		log.info("I am in Authentication");
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(getPasswordEncoder());
			
	}
	
	public  PasswordEncoder getPasswordEncoder() {
		
		PasswordEncoder encoder=new PasswordEncoder()
				{

					@Override
					public String encode(CharSequence rawPassword) {
					
						return rawPassword.toString();
					}

					@Override
					public boolean matches(CharSequence rawPassword, String encodedPassword) {
						
						return rawPassword.equals(encodedPassword);
					}
					
				};
		return encoder;
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
