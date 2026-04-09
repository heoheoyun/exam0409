package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.request.DepositRequestDto;
import com.example.dto.request.TransferRequestDto;
import com.example.dto.response.AccountResponseDto;
import com.example.dto.response.TransactionResponseDto;
import com.example.service.BankingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/banking")
@RequiredArgsConstructor
public class BankingController {

	private final BankingService bankingService;

	// 내 계좌 조회 (계좌번호, 잔액)
	@GetMapping("/account")
	public ResponseEntity<AccountResponseDto> getMyAccount() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok(bankingService.getMyAccount(username));
	}

	// 입금
	@PostMapping("/deposit")
	public ResponseEntity<String> deposit(@Valid @RequestBody DepositRequestDto dto) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		bankingService.deposit(username, dto);
		return ResponseEntity.ok("입금 완료");
	}

	// 송금
	@PostMapping("/transfer")
	public ResponseEntity<String> transfer(@Valid @RequestBody TransferRequestDto dto) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		bankingService.transfer(username, dto);
		return ResponseEntity.ok("송금 완료");
	}

	// 최근 거래 내역 5건 조회
	@GetMapping("/history")
	public ResponseEntity<List<TransactionResponseDto>> getHistory() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok(bankingService.getHistory(username));
	}
}
