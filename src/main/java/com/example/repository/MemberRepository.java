package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	// username으로 회원 조회
	Optional<Member> findByUsername(String username);

	// username 중복 여부 확인
	boolean existsByUsername(String username);
}
