package com.basic.bankingapp.service.impl;

import com.basic.bankingapp.dto.AccountDto;
import com.basic.bankingapp.entity.Account;
import com.basic.bankingapp.mapper.AccountMapper;
import com.basic.bankingapp.repository.AccountRepository;
import com.basic.bankingapp.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(AccountMapper::mapToAccountDto).toList();
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.deleteById(account.getId());
    }
}
