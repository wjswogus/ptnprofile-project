package com.cos.jwt.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.jwt.domain.user.User;
import com.cos.jwt.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReviewController {
	
	private final HttpSession session;
	private final ReviewService reviewService;
	
	@PostMapping("/review/write/{no}")
	public ResponseEntity<?> reviewWrite(HttpServletRequest request, @PathVariable int no, @RequestParam("type") String type, @RequestParam("rev_title") String title, @RequestParam("rev_content") String content, MultipartFile rev_img) {
		User user = (User) session.getAttribute("principal");
		reviewService.reviewWrite(request, no, user, type, title, content, rev_img);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
}
