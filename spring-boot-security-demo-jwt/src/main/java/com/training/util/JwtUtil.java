package com.training.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	private String secret="invenio@123";
	
	public String generateToken(String username)
	{
		Map<String,Object> claims = new HashMap<>();
		claims.put("project", "abc");
		claims.put("client_name", "client1");
		
		return Jwts
		.builder()
		.setClaims(claims)
		.setSubject(username)
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 1))
		.signWith(SignatureAlgorithm.HS256, secret)
		.compact();
	}

}


