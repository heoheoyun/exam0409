package com.example.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;
import com.example.entity.Member;

public interface AccountRepository extends JpaRepository<Account, Long> {

	// 회원으로 계좌 조회
	Optional<Account> findByMember(Member member);

	// 계좌번호로 계좌 조회
	Optional<Account> findByAccountNumber(String accountNumber);

	// 페이지네이션 계좌 조회
	Page<Account> findAll(Pageable pageable);
}
