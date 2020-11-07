package com.cos.jwt.domain.pt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PtRepository extends JpaRepository<Pt, Integer> {
	
	List<Pt> findByPtAddressContaining(String address);
}
