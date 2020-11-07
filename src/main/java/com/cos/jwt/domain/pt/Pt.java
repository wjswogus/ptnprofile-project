package com.cos.jwt.domain.pt;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
public class Pt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pt_no")
	private int ptNo;
	
	private String pt_name;
	@Column(name = "pt_address")
	private String ptAddress;
	private String pt_img;
	
	@Column(length = 100000)
	private String pt_content;
	
	private int pt_price;
	
	@JoinColumn(name = "pt_owner")
	@ManyToOne
	private User user;
	
	@JsonIgnoreProperties({"pt", "st"})
	@OneToMany(mappedBy = "pt")
	private List<MpOrder> orders;
}
