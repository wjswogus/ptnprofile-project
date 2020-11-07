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
import org.springframework.web.bind.annotation.RequestBody;import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwt.domain.user.User;
import com.cos.jwt.domain.user.UserDTO;
import com.cos.jwt.domain.user.UserRepository;
import com.cos.jwt.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {
	
	private final HttpSession session;
	private final UserService userService;
	private final UserRepository userRepository;
	
	@PostMapping("/user/join") // 유저 회원가입
	public ResponseEntity<?> join(@RequestBody User user) {
		System.out.println(user);
		userService.join(user);
		return new ResponseEntity<String>("ok", HttpStatus.CREATED);
	}
	
	@GetMapping("/user/info") // 유저 개인정보
	public ResponseEntity<?> userInfo() {
		User user = (User) session.getAttribute("principal");
		User userEntity = userService.info(user);
		return new ResponseEntity<User>(userEntity, HttpStatus.OK);
	}
 	
	@PutMapping("/user/modify") // 유저 수정
	public ResponseEntity<?> modify(@RequestBody UserDTO userDto) {
		User userEntity = (User) session.getAttribute("principal");
		System.out.println(userDto);
		userService.modify(userDto, userEntity);
		
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@DeleteMapping("/user/remove") // 유정 탈퇴
	public ResponseEntity<?> remove() {
		User user = (User) session.getAttribute("principal");
		System.out.println(user);
		int result = userService.remove(user);
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		
	}
	
	@GetMapping("/admin/userList") // 유저 목록
	public ResponseEntity<?> userList(@PageableDefault(sort = "userNo", direction = Direction.DESC, size = 15) Pageable page) {
		User admin = (User) session.getAttribute("principal");
		return new ResponseEntity<Page<User>>(userService.userList(admin, page), HttpStatus.OK);
	}
	
	@GetMapping("/user/logout") // 유저 로그아웃
	public ResponseEntity<?> logout() {
		session.invalidate();
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@PutMapping("/admin/userAuth/{no}")
	public ResponseEntity<?> userAuth(@PathVariable int no) {
		System.out.println(no);
		userService.userAuth(no);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
}
