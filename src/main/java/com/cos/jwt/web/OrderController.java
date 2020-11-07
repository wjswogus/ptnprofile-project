package com.cos.jwt.web;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwt.domain.user.User;
import com.cos.jwt.service.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderController {

	private final HttpSession session;
	private final OrderService orderService;
	
	@PostMapping("/order") // 상세보기에서 구매 or 장바구니에서 구매
	public ResponseEntity<?> test(@RequestParam("order_no") int[] order_no, @RequestParam("type") String type) {
		User user = (User) session.getAttribute("principal");
		orderService.order(user, order_no, type);
		
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
}
