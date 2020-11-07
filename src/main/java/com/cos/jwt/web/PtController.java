package com.cos.jwt.web;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.cos.jwt.domain.pt.Pt;
import com.cos.jwt.domain.user.User;
import com.cos.jwt.service.PtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PtController {
	
	private final PtService ptService;
	private final HttpSession session;
	
	@PostMapping("/pt/write") // 피티 등록
	public ResponseEntity<?> ptWrite(HttpServletRequest request, @RequestParam("pt_name") String pt_name, @RequestParam("pt_content") String pt_content, @RequestParam("pt_address") String pt_address, @RequestParam("pt_price") int pt_price, @RequestParam("pt_img") MultipartFile pt_img) {	
		User trainer = (User) session.getAttribute("principal");

		ptService.ptWrite(request, trainer, pt_name, pt_content, pt_address, pt_price, pt_img);	
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@GetMapping("/ptList") // 피티 목록
	public ResponseEntity<?> ptList(@PageableDefault(size = 9, sort = "ptNo", direction = Direction.DESC) Pageable pageable) {
		return new ResponseEntity<Page<Pt>>(ptService.ptList(pageable), HttpStatus.OK);
	}
	
	@GetMapping("/ptDetail/{no}") // 피티 상세보기
	public ResponseEntity<?> ptDetail(@PathVariable int no) {
		System.out.println("ptDetailController");
		return new ResponseEntity<Pt>(ptService.ptDetail(no), HttpStatus.OK);
	}
	
	@DeleteMapping("/pt/remove/{no}") // 피티 삭제
	public ResponseEntity<?> ptRemove(@PathVariable int no) {
		User trainer = (User) session.getAttribute("principal");
		int result = ptService.ptRemove(trainer, no);
		
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
	
	@PutMapping("/pt/modify/{no}") // 피티 수정
	public ResponseEntity<?> ptModify(@PathVariable int no, @RequestParam("pt_name") String pt_name, @RequestParam("pt_content") String pt_content, @RequestParam("pt_price") int pt_price, @RequestParam("pt_img") MultipartFile pt_img) {
		User trainer = (User) session.getAttribute("principal");
		int result = ptService.ptModify(trainer, no, pt_name, pt_content, pt_price, pt_img);
		
		if(result == 1) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
	
	@PostMapping("/ptSearch") // 피티 주소 검색
	public ResponseEntity<?> ptSearch(@RequestParam("keyword") String keyword, @RequestParam("type") String type) {
		System.out.println(keyword);
		
		
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
}
