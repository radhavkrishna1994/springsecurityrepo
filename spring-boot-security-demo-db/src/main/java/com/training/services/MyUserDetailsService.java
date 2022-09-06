package com.training.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.training.model.MyUser;
import com.training.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<MyUser> userOp = userRepo.findById(username);
		MyUser userFound=null;
		
		List<SimpleGrantedAuthority> list=new ArrayList<>();
		
		
		if(userOp.isPresent())
		{
			userFound=userOp.get();
			String roleString = userFound.getRole();
			for(String role:roleString.split(","))
			{
				list.add(new SimpleGrantedAuthority(role));
			}
			return new User(username,userFound.getPassword(),list);
		}
		
		return null;
	}

}
