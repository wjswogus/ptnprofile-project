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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwt.domain.board.Board;
import com.cos.jwt.domain.reply.BoardReply;
import com.cos.jwt.domain.user.User;
import com.cos.jwt.service.BoardReplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardReplyController {

	private final BoardReplyService boardReplyService;
	private final HttpSession session;
	
	@PostMapping("/board/reply/{no}")
	public ResponseEntity<?> replyWrite(@RequestBody BoardReply reply, @PathVariable int no) {
		User user = (User) session.getAttribute("principal");
		boardReplyService.replyWrite(reply, user, no); 
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@DeleteMapping("/board/reply/{no}")
	public ResponseEntity<?> replyRemove(@PathVariable int no) {		
		int result = boardReplyService.replyRemove(no);
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
}
