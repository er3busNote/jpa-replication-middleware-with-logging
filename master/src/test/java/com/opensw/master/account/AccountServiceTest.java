package com.opensw.master.account;

import com.opensw.master.domain.account.domain.Account;
import com.opensw.master.domain.account.dto.AccountDto;
import com.opensw.master.domain.account.application.AccountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
    
    @Autowired
    AccountService accountService;
    
    @Test
    @DisplayName("주소록 저장 여부 확인")
    public void saveAccount() {
        // Given (준비)
        String username = "byoungJu";
        String email = "byoungJu@email.com";
        String phone = "010-5145-9132";
        AccountDto accountDto = new AccountDto(username, email, phone);

        // When (실행)
        Account account = this.accountService.saveAccount(accountDto);
        Optional<Account> optionalAccount = accountService.getAccount(account.getId());

        // Then (검증)
        Assertions.assertThat(optionalAccount.get().getUsername()).isEqualTo(accountDto.getUsername());
        Assertions.assertThat(optionalAccount.get().getEmail()).isEqualTo(accountDto.getEmail());
        Assertions.assertThat(optionalAccount.get().getPhone()).isEqualTo(accountDto.getPhone());
    }
}
