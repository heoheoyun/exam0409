package com.example.entity;

import java.time.LocalDateTime;

import com.example.entity.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Bank_Transaction")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_account_id")
	private Account senderAccount; // 보내는 계좌 (입금 시 null)

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_account_id")
	private Account receiverAccount; // 받는 계좌

	private Long amount;

	@Enumerated(EnumType.STRING)
	private TransactionType type;

	private LocalDateTime createdAt = LocalDateTime.now();
}
