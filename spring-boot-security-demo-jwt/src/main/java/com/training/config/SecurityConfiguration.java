package com.training.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.training.filters.JwtFilter;
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

	@Autowired
	private JwtFilter jwtFilter;
	
	public void configure(HttpSecurity http) throws Exception
	{
		log.info("In Authorization....");
		
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/authenticate")
		.permitAll()
		.antMatchers("/user/**")
		//.hasRole("USER")
		.hasAnyRole("USER","ADMIN")
		.antMatchers("/admin/**")
		.hasRole("ADMIN")
		.anyRequest().authenticated();
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Bean
	public AuthenticationManager getAuthManager() throws Exception
	{
		return super.authenticationManager();
	}
	
	
}
