package com.cos.jwt.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cos.jwt.domain.pt.Pt;
import com.cos.jwt.domain.pt.PtRepository;
import com.cos.jwt.domain.st.St;
import com.cos.jwt.domain.st.StRepository;
import com.cos.jwt.domain.user.User;
import com.cos.jwt.domain.user.UserRepository;
import com.cos.jwt.domain.wish.MpWish;
import com.cos.jwt.domain.wish.MpWishRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WishService {
	
	private final MpWishRepository mpWishRepository;
	private final UserRepository userRepository;
	private final PtRepository ptRepository;
	private final StRepository stRepository;
	
	@Transactional
	public void wish(User user, int no, String type) { // st or pt 구분해서 장바구니에 집어 넣기
		User userEntity = userRepository.findById(user.getUserNo()).orElseThrow(() -> new IllegalArgumentException(user + " 회원은 없습니다."));
		
		if(type.equals("pt")) {
			Pt pt = ptRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번의 피티는 없습니다."));
			MpWish wish = new MpWish();
			wish.setUser(userEntity);
			wish.setPt(pt);
			mpWishRepository.save(wish);
		} else {
			St st = stRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번의 스튜디오는 없습니다."));
			MpWish wish = new MpWish();
			wish.setUser(userEntity);
			wish.setSt(st);
			mpWishRepository.save(wish);
		}
	}
	
	@Transactional
	public int wishRemove(User user, int no) {
		User userEntity = userRepository.findById(user.getUserNo()).orElseThrow(() -> new IllegalArgumentException(user + " 회원은 없습니다."));
		
		MpWish mpWish = mpWishRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번의 상품은 없습니다."));
		if(userEntity.getUserNo() == mpWish.getUser().getUserNo()) {
			mpWishRepository.deleteById(no);
			return 1;
		} else {
			return 0;
		}
		
	}
	
	@Transactional
	public List<MpWish> wishInfo(User user) {
		User userEntity = userRepository.findById(user.getUserNo()).orElseThrow(() -> new IllegalArgumentException("회원은 없습니다"));
		System.out.println(userEntity.getUserNo());
		List<MpWish> wish = mpWishRepository.findByUserId(userEntity);
		for(int i=0; i<wish.size(); i++) {
			System.out.println(wish.get(i).getPt());
		}
		return mpWishRepository.findByUserId(userEntity);
	}
}
