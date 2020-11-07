package com.cos.jwt.service;

import java.util.List;

import javax.transaction.Transactional;

import org.dom4j.IllegalAddException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cos.jwt.domain.board.Board;
import com.cos.jwt.domain.board.BoardRepository;
import com.cos.jwt.domain.reply.BoardReply;
import com.cos.jwt.domain.reply.ReplyRepository;
import com.cos.jwt.domain.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardReplyService {
	
	private final ReplyRepository replyRepository;
	private final BoardRepository boardRepository;
	
	@Transactional
	public void replyWrite(BoardReply reply, User user, int boardNo) {
		Board board = boardRepository.findById(boardNo).orElseThrow(() -> new IllegalArgumentException(boardNo + "는 존재하지 않습니다."));
		reply.setBoard(board);
		reply.setUser(user);
		replyRepository.save(reply);
	}
	
	@Transactional
	public int replyRemove(int no) {
		BoardReply reply = replyRepository.findById(no).orElseThrow(() -> new IllegalAddException(no + "는 존재하지 않습니다."));
		
		if(reply != null) {
			replyRepository.deleteById(no);
			return 1;
		} else {
			return 0;
		}
	}
}
