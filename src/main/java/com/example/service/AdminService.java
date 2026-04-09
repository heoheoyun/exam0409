package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.response.AccountResponseDto;
import com.example.dto.response.MemberAccountResponseDto;
import com.example.entity.Account;
import com.example.entity.Member;
import com.example.repository.AccountRepository;
import com.example.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final MemberRepository memberRepository;
	private final AccountRepository accountRepository;

	// 전체 회원 아이디 목록 조회
	public List<String> getAllMembers() {
		return memberRepository.findAll().stream().map(Member::getUsername).collect(Collectors.toList());
	}

	// 전체 회원 계좌 및 잔액 목록 조회
	public List<MemberAccountResponseDto> getAllAccounts() {
		return accountRepository.findAll().stream().map(MemberAccountResponseDto::new).collect(Collectors.toList());
	}

	// 전체 계좌 잔액 총합 조회
	public Long getTotalBalance() {
		return accountRepository.findAll().stream().mapToLong(Account::getBalance).sum();
	}

	// 특정 계좌 동결
	@Transactional
	public AccountResponseDto freeze(Long accountId) {
		Account account = accountRepository.findById(accountId).orElseThrow();
		account.setFrozen(true);
		accountRepository.save(account);
		return new AccountResponseDto(account);
	}

	// 특정 계좌 동결 해제
	@Transactional
	public AccountResponseDto unfreeze(Long accountId) {
		Account account = accountRepository.findById(accountId).orElseThrow();
		account.setFrozen(false);
		accountRepository.save(account);
		return new AccountResponseDto(account);
	}
}
