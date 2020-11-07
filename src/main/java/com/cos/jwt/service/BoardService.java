package com.cos.jwt.service;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cos.jwt.domain.board.Board;
import com.cos.jwt.domain.board.BoardDTO;
import com.cos.jwt.domain.board.BoardRepository;
import com.cos.jwt.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	@Transactional
	public Page<Board> boardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional
	public void boardWrite(Board board, User user) {
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional
	public Board boardDetail(int no) {
		return boardRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번 게시물은 존재하지 않습니다."));
	}
	
	@Transactional
	public int boardRemove(int no, User user) {
		Board board = boardRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번 게시물은 존재하지 않습니다."));
		
		if(board.getUser().getId().equals(user.getId())) {
			boardRepository.deleteById(no);
			return 1;
		} else {
			return 0;
		}
	}
	
	@Transactional
	public int boardModify(BoardDTO dto, int no, User user) {
		Board board = boardRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번 게시물은 존재하지 않습니다."));
		
		if(board.getUser().getId().equals(user.getId())) {
			board.setTitle(dto.getTitle());
			board.setContent(dto.getContent());
			return 1;
		} else {
			return 0;
		}
	}
}
