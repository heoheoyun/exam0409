package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.response.AccountResponseDto;
import com.example.dto.response.PagedAccountResponseDto;
import com.example.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	// 전체 회원 아이디 목록 조회
	@GetMapping("/members")
	public ResponseEntity<List<String>> getAllMembers() {
		return ResponseEntity.ok(adminService.getAllMembers());
	}

	// 전체 회원 계좌 목록 페이지네이션 조회
	@GetMapping("/accounts/{page}")
	public ResponseEntity<PagedAccountResponseDto> getAccountsPage(@PathVariable int page) {
		return ResponseEntity.ok(adminService.getAccountsPage(page));
	}

	// 전체 계좌 잔액 총합 조회
	@GetMapping("/total-balance")
	public ResponseEntity<Long> getTotalBalance() {
		return ResponseEntity.ok(adminService.getTotalBalance());
	}

	// 특정 계좌 동결
	@PostMapping("/account/{accountId}/freeze")
	public ResponseEntity<AccountResponseDto> freeze(@PathVariable Long accountId) {
		return ResponseEntity.ok(adminService.freeze(accountId));
	}

	// 특정 계좌 동결 해제
	@PostMapping("/account/{accountId}/unfreeze")
	public ResponseEntity<AccountResponseDto> unfreeze(@PathVariable Long accountId) {
		return ResponseEntity.ok(adminService.unfreeze(accountId));
	}
}