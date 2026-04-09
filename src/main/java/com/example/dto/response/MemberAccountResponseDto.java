package com.example.dto.response;

import com.example.entity.Account;
import lombok.Getter;

@Getter
public class MemberAccountResponseDto {
    private Long accountId;
    private String username;
    private String accountNumber;
    private Long balance;
    private boolean frozen;

    public MemberAccountResponseDto(Account account) {
        this.accountId = account.getId();
        this.username = account.getMember().getUsername();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
        this.frozen = account.isFrozen();
    }
}
