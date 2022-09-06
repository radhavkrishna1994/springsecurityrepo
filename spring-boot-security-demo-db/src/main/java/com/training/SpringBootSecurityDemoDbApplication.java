package com.training;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.training.model.MyUser;
import com.training.repo.UserRepo;

@SpringBootApplication
public class SpringBootSecurityDemoDbApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoDbApplication.class, args);
	}
	
	@Autowired
	private UserRepo userRepo;
	
	@PostConstruct
	public void init()
	{
		userRepo.save(new MyUser("user1", "user1", "USER_ROLE"));
		userRepo.save(new MyUser("admin", "admin123", "ADMIN_ROLE"));
	}

	@Override
	public void run(String... args) throws Exception {
		
		userRepo.findAll().forEach(u->System.out.println(u));
		
	}

	
}
