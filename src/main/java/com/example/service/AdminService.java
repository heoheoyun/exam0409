package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.response.AccountResponseDto;
import com.example.dto.response.MemberAccountResponseDto;
import com.example.dto.response.PagedAccountResponseDto;
import com.example.entity.Account;
import com.example.entity.Member;
import com.example.repository.AccountRepository;
import com.example.repository.MemberRepository;
import com.example.util.PageHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final MemberRepository memberRepository;
	private final AccountRepository accountRepository;

	private static final int PAGE_SIZE = 5;

	// 전체 회원 아이디 목록 조회
	public List<String> getAllMembers() {
		return memberRepository.findAll().stream().map(Member::getUsername).collect(Collectors.toList());
	}

	// 전체 회원 계좌 목록 페이지네이션 조회
	public PagedAccountResponseDto getAccountsPage(int page) {
		// 유효하지 않은 page 값 보정
		if (page < 1)
			page = 1;

		PageRequest pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by("id").ascending());
		Page<Account> accountPage = accountRepository.findAll(pageable);

		List<MemberAccountResponseDto> accounts = accountPage.getContent().stream().map(MemberAccountResponseDto::new)
				.collect(Collectors.toList());

		// findAll() 중복 호출 제거 - 페이지 조회 결과에서 총합 계산
		long totalBalance = accountPage.getContent().stream().mapToLong(Account::getBalance).sum();

		PageHandler pageHandler = new PageHandler((int) accountPage.getTotalElements(), page, PAGE_SIZE);

		return new PagedAccountResponseDto(accounts, pageHandler, totalBalance);
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