package com.example.repository;

import com.example.entity.Account;
import com.example.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // 내 계좌 기준 (보낸 것 + 받은 것) 최근 5건 조회
    List<Transaction> findTop5BySenderAccountOrReceiverAccountOrderByCreatedAtDesc(
            Account senderAccount, Account receiverAccount);
}
