package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.request.DepositRequestDto;
import com.example.dto.request.TransferRequestDto;
import com.example.dto.response.AccountResponseDto;
import com.example.dto.response.TransactionResponseDto;
import com.example.entity.Account;
import com.example.entity.Member;
import com.example.entity.Transaction;
import com.example.entity.enums.TransactionType;
import com.example.repository.AccountRepository;
import com.example.repository.MemberRepository;
import com.example.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankingService {

	private final MemberRepository memberRepository;
	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;

	// 내 계좌 정보 조회 (계좌번호, 잔액)
	public AccountResponseDto getMyAccount(String username) {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
		Account account = accountRepository.findByMember(member)
				.orElseThrow(() -> new RuntimeException("계좌를 찾을 수 없습니다."));
		return new AccountResponseDto(account);
	}

	// 입금 - 내 계좌 잔액 증가 후 거래 내역 저장
	@Transactional
	public void deposit(String username, DepositRequestDto dto) {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
		Account account = accountRepository.findByMember(member)
				.orElseThrow(() -> new RuntimeException("계좌를 찾을 수 없습니다."));

		if (account.isFrozen()) {
			throw new RuntimeException("동결된 계좌입니다.");
		}

		account.setBalance(account.getBalance() + dto.getAmount());
		accountRepository.save(account);

		Transaction tx = new Transaction();
		tx.setReceiverAccount(account);
		tx.setAmount(dto.getAmount());
		tx.setType(TransactionType.DEPOSIT);
		transactionRepository.save(tx);
	}

	// 송금 - 잔액 부족 시 롤백, 출금/입금 동시 처리
	@Transactional
	public void transfer(String username, TransferRequestDto dto) {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
		Account myAccount = accountRepository.findByMember(member)
				.orElseThrow(() -> new RuntimeException("계좌를 찾을 수 없습니다."));

		if (myAccount.isFrozen()) {
			throw new RuntimeException("내 계좌가 동결되어 있습니다.");
		}
		if (myAccount.getBalance() < dto.getAmount()) {
			throw new RuntimeException("잔액이 부족합니다.");
		}

		Account targetAccount = accountRepository.findByAccountNumber(dto.getReceiverAccountNumber())
				.orElseThrow(() -> new RuntimeException("존재하지 않는 계좌번호입니다."));

		if (targetAccount.isFrozen()) {
			throw new RuntimeException("수신 계좌가 동결된 계좌입니다.");
		}

		myAccount.setBalance(myAccount.getBalance() - dto.getAmount());
		targetAccount.setBalance(targetAccount.getBalance() + dto.getAmount());
		accountRepository.save(myAccount);
		accountRepository.save(targetAccount);

		Transaction tx = new Transaction();
		tx.setSenderAccount(myAccount);
		tx.setReceiverAccount(targetAccount);
		tx.setAmount(dto.getAmount());
		tx.setType(TransactionType.TRANSFER);
		transactionRepository.save(tx);
	}

	// 최근 거래 내역 5건 조회
	public List<TransactionResponseDto> getHistory(String username) {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
		Account account = accountRepository.findByMember(member)
				.orElseThrow(() -> new RuntimeException("계좌를 찾을 수 없습니다."));
		return transactionRepository.findTop5BySenderAccountOrReceiverAccountOrderByCreatedAtDesc(account, account)
				.stream().map(TransactionResponseDto::new).collect(Collectors.toList());
	}
}
