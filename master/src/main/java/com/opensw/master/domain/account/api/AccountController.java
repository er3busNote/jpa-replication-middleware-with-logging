package com.opensw.master.domain.account.api;


import com.opensw.master.domain.account.application.AccountService;
import com.opensw.master.domain.account.domain.Account;
import com.opensw.master.domain.account.dto.AccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getAccount(
        @PathVariable Integer id
    ) {
        Optional<Account> optionalAccount = accountService.getAccount(id);
        if (!optionalAccount.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalAccount.get());
    }

    @PostMapping("/store")
    public ResponseEntity storeAccount(
        final @Valid @RequestBody AccountDto accountDto
    ) {
        return ResponseEntity.ok(accountService.saveAccount(accountDto));
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(errors);
    }
}
