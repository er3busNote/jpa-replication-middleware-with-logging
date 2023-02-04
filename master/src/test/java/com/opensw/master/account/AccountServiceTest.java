package com.opensw.master.account;

import com.opensw.master.domain.account.application.AccountService;
import com.opensw.master.domain.account.domain.Account;
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
        Account data = Account.builder()
                .username(username)
                .email(email)
                .phone(phone)
                .build();

        // When (실행)
        Account account = this.accountService.saveAccount(data);
        Optional<Account> optionalAccount = accountService.getAccount(account.getId());

        // Then (검증)
        Assertions.assertThat(optionalAccount.get().getId()).isEqualTo(data.getId());
        Assertions.assertThat(optionalAccount.get().getUsername()).isEqualTo(data.getUsername());
    }
}
