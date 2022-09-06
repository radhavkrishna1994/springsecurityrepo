package com.training.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class MyUser {

	@Id
	@Column(name = "username",length = 100)
	private String username;
	private String password;
	private String role;
}
