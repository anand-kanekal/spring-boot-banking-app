package com.basic.bankingapp.controller;

import com.basic.bankingapp.dto.AccountDto;
import com.basic.bankingapp.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto account) {
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable(value = "id") Long id, @RequestBody Map<String, Double> request) {
        Double depositAmount = request.get("amount");
        return new ResponseEntity<>(accountService.deposit(id, depositAmount), HttpStatus.OK);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable(value = "id") Long id, @RequestBody Map<String, Double> request) {
        Double withdrawAmount = request.get("amount");
        return new ResponseEntity<>(accountService.withdraw(id, withdrawAmount), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable(value = "id") Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
    }
}