package com.cos.jwt.web;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.jwt.domain.board.Board;
import com.cos.jwt.domain.board.BoardDTO;
import com.cos.jwt.domain.user.User;
import com.cos.jwt.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardController {
	
	private final BoardService boardService;
	private final HttpSession session;
	
	@GetMapping("/boardList")
	public ResponseEntity<?> list(@PageableDefault(size = 10, sort = "boardNo", direction = Direction.DESC) Pageable pageable) {
		return new ResponseEntity<Page<Board>>(boardService.boardList(pageable), HttpStatus.OK);
	}
	
	@PostMapping("/board/write")
	public ResponseEntity<?> write(@RequestBody Board board) {
		User user = (User)session.getAttribute("principal");
		boardService.boardWrite(board, user);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@GetMapping("/boardDetail/{no}")
	public ResponseEntity<?> detail(@PathVariable int no) {
		return new ResponseEntity<Board>(boardService.boardDetail(no), HttpStatus.OK);
	}
	
	@DeleteMapping("/board/delete/{no}")
	public ResponseEntity<?> remove(@PathVariable int no) {
		User user = (User)session.getAttribute("principal");
		int result = boardService.boardRemove(no, user);
		
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
	
	@PutMapping("/board/modify/{no}")
	public ResponseEntity<?> modify(@PathVariable int no, @RequestBody BoardDTO dto) {
		User user = (User)session.getAttribute("principal");
		int result = boardService.boardModify(dto, no, user);

		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping
	public void Test(MultipartFile file) {
		
	}
	
	
	
	
	
	
}
