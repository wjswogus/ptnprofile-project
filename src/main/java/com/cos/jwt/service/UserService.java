package com.cos.jwt.service;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cos.jwt.domain.user.User;
import com.cos.jwt.domain.user.UserDTO;
import com.cos.jwt.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	@Transactional // 유저 회원가입
	public void join(User user) {
		userRepository.save(user);
	}
	
	@Transactional // 유저 수정
	public void modify(UserDTO userDto, User user) {
		User userModify = userRepository.findById(user.getUserNo()).orElseThrow(() -> new IllegalArgumentException(user + "없다."));
		userModify.setAddress(userDto.getAddress());
		userModify.setName(userDto.getName());
		userModify.setEmail(userDto.getEmail());
		userModify.setAuth_pt(userDto.getAuth_pt());
	}
	
	@Transactional // 유정 정보
	public User info(User user) {
		User userEntity = userRepository.findById(user.getUserNo()).orElseThrow(() -> new IllegalArgumentException("없다"));
		return userEntity;
	}
	
	@Transactional // 유저 탈퇴
	public int remove(User user) {
		System.out.println(user);
		if(user != null) {
			userRepository.deleteById(user.getUserNo());
			return 1;
		} else {
			return 0;
		}
	}
	
	@Transactional // 유저 목록
	public Page<User> userList(User admin, Pageable page) {
		return userRepository.findAll(page);
	}
	
	@Transactional // 트레이너 피티 등록 권한 주기
	public void userAuth(int userNo) {
		User trainer = userRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException(userNo + "번의 유저는 없습니다."));
		
		if(trainer != null) {
			trainer.setAuth_pt(3);
			System.out.println("권한 주기 성공!!");
		} else {
			System.out.println("권한 주기 실패!!");
		}
	}
}
