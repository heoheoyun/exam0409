package com.example.repository;

import com.example.entity.Account;
import com.example.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // 회원으로 계좌 조회
    Optional<Account> findByMember(Member member);

    // 계좌번호로 계좌 조회
    Optional<Account> findByAccountNumber(String accountNumber);
}
