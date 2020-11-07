package com.cos.jwt.domain.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.cos.jwt.domain.order.MpOrder;
import com.cos.jwt.domain.pt.Pt;
import com.cos.jwt.domain.wish.MpWish;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_no")
	private int userNo;
	
	private String address;
	private int auth_pt;
	private String email;
	
	@Column(unique = true)
	private String id;
	private String password;
	private String name;
	
	
	@JsonIgnoreProperties("user")
	@OneToMany(mappedBy = "user")
	private List<MpOrder> orders;
	
	@JsonIgnoreProperties("user")
	@OneToMany(mappedBy = "user")
	private List<MpWish> wishs;
	
	@JsonIgnoreProperties({"user", "orders"})
	@OneToMany(mappedBy = "user")
	private List<Pt> pts;
}
