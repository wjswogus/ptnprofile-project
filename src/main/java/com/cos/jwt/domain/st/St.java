package com.cos.jwt.domain.st;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.cos.jwt.domain.order.MpOrder;
import com.cos.jwt.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class St {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "st_no")
	private int stNo;
	
	private String st_name;
	private String st_address;
	private String st_img;
	
	@Column(length = 100000)
	private String st_content;
	
	private int st_price;
	
	@ManyToOne
	@JoinColumn(name = "st_owner")
	private User user;
	
	@JsonIgnoreProperties({"pt", "st"})
	@OneToMany(mappedBy = "st")
	private List<MpOrder> orders;
}
