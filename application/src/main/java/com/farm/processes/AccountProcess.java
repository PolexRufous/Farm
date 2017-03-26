package com.farm.processes;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.accounts.AccountRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
@Transactional
public class AccountProcess
{
  @Resource
  private AccountRepository accountRepository;

  // TODO: 26.02.2017 implement method
  public Account findOrCreateByAccountNumber(String accountNumber){
    return null;
  }
}
