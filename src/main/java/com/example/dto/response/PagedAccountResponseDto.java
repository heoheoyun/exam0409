package com.example.dto.response;

import com.example.util.PageHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

// 페이지네이션 정보 + 계좌 목록을 함께 반환하는 DTO
@Getter
@AllArgsConstructor
public class PagedAccountResponseDto {
    private List<MemberAccountResponseDto> accounts;
    private PageHandler pageHandler;
    private long totalBalance;
}
