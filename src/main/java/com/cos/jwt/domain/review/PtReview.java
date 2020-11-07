package com.cos.jwt.domain.review;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cos.jwt.domain.pt.Pt;
import com.cos.jwt.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PtReview {
	@Id
	@Column(name = "review_no")
	private int reviewNo;
	
	@JoinColumn(name = "user_no")
	@ManyToOne
	private User user;
	
	@JoinColumn(name = "pt_no")
	@ManyToOne
	private Pt pt;
	
	private String pt_rev_img_name;
	private String pt_rev_title;
	
	@Column(length = 100000)
	private String pt_rev_content;
}
