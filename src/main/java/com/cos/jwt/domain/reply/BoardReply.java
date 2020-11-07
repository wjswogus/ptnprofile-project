package com.cos.jwt.domain.reply;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cos.jwt.domain.board.Board;
import com.cos.jwt.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardReply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "b_re_no")
	private int boardReplyNo;
	
	@Column(length = 100000)
	private String b_re_content;
	
	@JoinColumn(name = "b_re_board_no")
	@ManyToOne
	private Board board;
	
	@JsonIgnoreProperties({"orders", "wishs", "pts"})
	@JoinColumn(name = "b_re_user_no")
	@ManyToOne
	private User user;
}
