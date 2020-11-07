package com.cos.jwt.domain.wish;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.jwt.domain.order.MpOrder;
import com.cos.jwt.domain.pt.Pt;
import com.cos.jwt.domain.st.St;
import com.cos.jwt.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MpWish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wish_no")
	private int wishNo;
	
	@ManyToOne
	@JoinColumn(name = "user_no")
	private User user;
	
	@ManyToOne
	@JsonIgnoreProperties({"user", "orders"})
	@JoinColumn(name = "wish_pt_no")
	private Pt pt;
	
	@ManyToOne
	@JsonIgnoreProperties({"user", "orders"})
	@JoinColumn(name = "wish_st_no")
	private St st;
	
	@CreationTimestamp
	private Timestamp orderDate;
}
