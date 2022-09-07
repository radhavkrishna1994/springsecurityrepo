package com.training.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.training.services.MyUserDetailsService;
import com.training.util.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {

		String authorizationHeader = request.getHeader("Authorization");
		//Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsInByb2plY3QiOiJhYmMiLCJleHAiOjE2NTI4NTAxMTcsImNsaWVudF9uYW1lIjoiY2xpZW50MSIsImlhdCI6MTY1Mjg0NjUxN30.oeebS9MMv7WyF9U1Wsjg_fXdcW6OO0nJdodUh4vfz_A
		String token=null;
		String username=null;

		if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer"))
		{
			token = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(token);
		}

		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{	//validate the username
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);//from db

			if(jwtUtil.validateToken(token, userDetails))
			{
				//authentication
				UsernamePasswordAuthenticationToken upToken=new 
						UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());

				upToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(upToken);
			}


		}
		filterChain.doFilter(request, response);

	}
}