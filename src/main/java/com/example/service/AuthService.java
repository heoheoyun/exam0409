package com.example.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.request.LoginRequestDto;
import com.example.dto.request.SignUpRequestDto;
import com.example.dto.response.LoginResponseDto;
import com.example.entity.Account;
import com.example.entity.Member;
import com.example.jwt.JwtUtil;
import com.example.repository.AccountRepository;
import com.example.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	// 회원가입 - 비밀번호 암호화 후 저장, 기본 계좌 자동 생성
	@Transactional
	public void signUp(SignUpRequestDto dto) {
		if (memberRepository.existsByUsername(dto.getUsername())) {
			throw new RuntimeException("이미 사용 중인 아이디입니다.");
		}

		Member member = new Member();
		member.setUsername(dto.getUsername());
		member.setPassword(passwordEncoder.encode(dto.getPassword()));
		member.setName(dto.getName());
		member.setRole(dto.getRole());
		memberRepository.save(member);

		// 계좌번호 형식: 000-0000-0000
		Account account = new Account();
		String accountNumber = String.format("%03d-%04d-%04d", (int) (Math.random() * 900) + 100,
				(int) (Math.random() * 9000) + 1000, (int) (Math.random() * 9000) + 1000);
		account.setAccountNumber(accountNumber);
		account.setMember(member);
		accountRepository.save(account);
	}

	// 로그인 - 아이디/비밀번호 확인 후 JWT 발급
	public LoginResponseDto login(LoginRequestDto dto) {
		Member member = memberRepository.findByUsername(dto.getUsername())
				.orElseThrow(() -> new RuntimeException("아이디 또는 비밀번호가 틀렸습니다."));

		if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
			throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다.");
		}

		String token = jwtUtil.generateToken(member.getUsername(), member.getRole().name());
		return new LoginResponseDto(token);
	}
}
