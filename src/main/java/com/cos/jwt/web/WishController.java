package com.cos.jwt.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwt.domain.user.User;
import com.cos.jwt.domain.wish.MpWish;
import com.cos.jwt.service.WishService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class WishController {

	private final HttpSession session;
	private final WishService wishService;

	@PostMapping("/wish/{no}") // st or pt 구분해서 장바구니에 집어 넣기
	public ResponseEntity<?> wish(@PathVariable int no, @RequestParam("type") String type) {
		System.out.println(no);
		System.out.println(type);
		User user = (User) session.getAttribute("principal");
		wishService.wish(user, no, type);

		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@DeleteMapping("/wish/{no}")
	public ResponseEntity<?> wishRemove(@PathVariable int no) {
		System.out.println(no);
		User user = (User) session.getAttribute("principal");
		System.out.println(user.getAddress());

		int result = wishService.wishRemove(user, no);

		if (result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail" ,HttpStatus.FORBIDDEN);
		}
	}
	
	@GetMapping("/wish/info")
	public ResponseEntity<?> wishInfo() {
		User user = (User) session.getAttribute("principal");
		return new ResponseEntity<List<MpWish>>(wishService.wishInfo(user), HttpStatus.OK);
		
	}
}
