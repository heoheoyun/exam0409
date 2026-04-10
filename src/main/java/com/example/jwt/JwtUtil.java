package com.example.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKeyString;

	@Value("${jwt.expiration}")
	private long expirationMs;

	private SecretKey secretKey;

	@PostConstruct
	public void init() {
		this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
	}

	// Access Token 생성
	public String generateToken(String username, String role) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationMs);

		return Jwts.builder().subject(username).claim("role", role).issuedAt(now).expiration(expiry).signWith(secretKey)
				.compact();
	}

	// 토큰에서 username 추출
	public String getUsername(String token) {
		return parseClaims(token).getSubject();
	}

	// 토큰에서 role 추출
	public String getRole(String token) {
		return parseClaims(token).get("role", String.class);
	}

	// 토큰 유효성 검증
	public boolean validateToken(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (ExpiredJwtException e) {
			log.warn("만료된 JWT 토큰: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.warn("지원하지 않는 JWT 토큰: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.warn("잘못된 JWT 토큰: {}", e.getMessage());
		} catch (SecurityException e) {
			log.warn("잘못된 JWT 서명: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.warn("JWT 클레임이 비어있음: {}", e.getMessage());
		}
		return false;
	}

	private Claims parseClaims(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
	}
}
