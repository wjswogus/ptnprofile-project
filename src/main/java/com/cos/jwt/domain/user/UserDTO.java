package com.cos.jwt.domain.user;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private String address;
	private int auth_pt;
	private String email;
	private String password;
	private String name;
}
