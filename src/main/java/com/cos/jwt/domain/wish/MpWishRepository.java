package com.cos.jwt.domain.wish;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.jwt.domain.user.User;

public interface MpWishRepository extends JpaRepository<MpWish, Integer> {
	
	List<MpWish> findByUserId(User user);
}
