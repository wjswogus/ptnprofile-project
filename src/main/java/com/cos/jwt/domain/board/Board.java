package com.cos.jwt.domain.board;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.jwt.domain.reply.BoardReply;
import com.cos.jwt.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_no")
	private int boardNo;
	
	private String title;
	private String content;
	
	@JsonIgnoreProperties({"board", "orders", "wishs", "pts"})
	@JoinColumn(name = "b_user_no")
	@ManyToOne
	private User user;
	
	@JsonIgnoreProperties({"board"})
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	private List<BoardReply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
}
