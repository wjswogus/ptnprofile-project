package com.cos.jwt.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cos.jwt.domain.order.MpOrder;
import com.cos.jwt.domain.order.MpOrderRepository;
import com.cos.jwt.domain.pt.Pt;
import com.cos.jwt.domain.pt.PtRepository;
import com.cos.jwt.domain.st.St;
import com.cos.jwt.domain.st.StRepository;
import com.cos.jwt.domain.user.User;
import com.cos.jwt.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {
	
	private final UserRepository userRepository;
	private final PtRepository ptRepository;
	private final StRepository stRepository;
	private final MpOrderRepository mpOrderRepository;
	
	@Transactional
	public void order(User user, int[] order_no, String type) {
		User userEntity = userRepository.findById(user.getUserNo()).orElseThrow(() -> new IllegalArgumentException(user + "는 없는 회원입니다."));
		
		for(int i=0; i<order_no.length; i++) {
			if(type.equals("pt")) {
				MpOrder order = new MpOrder();
				Pt pt = ptRepository.findById(order_no[i]).get();
				order.setUser(userEntity);
				order.setPt(pt);
				mpOrderRepository.save(order);
			} else {
				MpOrder order = new MpOrder();
				St st = stRepository.findById(order_no[i]).get();
				order.setUser(userEntity);
				order.setSt(st);
				mpOrderRepository.save(order);
			}
		}
	}
}
