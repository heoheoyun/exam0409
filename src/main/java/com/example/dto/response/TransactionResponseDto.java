package com.example.dto.response;

import java.time.LocalDateTime;

import com.example.entity.Transaction;
import com.example.entity.enums.TransactionType;

import lombok.Getter;

@Getter
public class TransactionResponseDto {
	private String senderAccountNumber;
	private String receiverAccountNumber;
	private Long amount;
	private TransactionType type;
	private LocalDateTime createdAt;

	public TransactionResponseDto(Transaction t) {
		this.senderAccountNumber = t.getSenderAccount() != null ? t.getSenderAccount().getAccountNumber() : null;
		this.receiverAccountNumber = t.getReceiverAccount().getAccountNumber();
		this.amount = t.getAmount();
		this.type = t.getType();
		this.createdAt = t.getCreatedAt();
	}
}
