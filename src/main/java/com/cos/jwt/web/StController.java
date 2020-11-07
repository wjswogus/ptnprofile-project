package com.cos.jwt.web;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.jwt.domain.st.St;
import com.cos.jwt.domain.user.User;
import com.cos.jwt.service.StService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class StController {
	
	private final StService stService;
	private final HttpSession session;
	
	@PostMapping("/st/write")
	public ResponseEntity<?> stWrite(HttpServletRequest request, @RequestParam("st_name") String st_name, @RequestParam("st_content") String st_content, @RequestParam("st_address") String st_address, @RequestParam("st_price") int st_price, @RequestParam("st_img") MultipartFile st_img) {	
		User trainer = (User) session.getAttribute("principal");

		stService.stWrite(request, trainer, st_name, st_content, st_address, st_price, st_img);	
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@GetMapping("/stList")
	public ResponseEntity<?> stList(@PageableDefault(size = 9, sort = "ptNo", direction = Direction.DESC) Pageable pageable) {
		return new ResponseEntity<Page<St>>(stService.stList(pageable), HttpStatus.OK);
	}
	
	@GetMapping("/stDetail/{no}")
	public ResponseEntity<?> stDetail(@PathVariable int no) {
		System.out.println("stDetailController");
		return new ResponseEntity<St>(stService.stDetail(no), HttpStatus.OK);
	}
	
	@DeleteMapping("/st/remove/{no}")
	public ResponseEntity<?> stRemove(@PathVariable int no) {
		User trainer = (User) session.getAttribute("principal");
		int result = stService.stRemove(trainer, no);
		
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
	
	@PutMapping("/st/modify/{no}")
	public ResponseEntity<?> stModify(@PathVariable int no, @RequestParam("pt_name") String pt_name, @RequestParam("pt_content") String pt_content, @RequestParam("pt_price") int pt_price, @RequestParam("pt_img") MultipartFile pt_img) {
		User trainer = (User) session.getAttribute("principal");
		int result = stService.stModify(trainer, no, pt_name, pt_content, pt_price, pt_img);
		
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
}
