package com.farm.database.processes;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.accounts.AccountBalance;
import com.farm.database.entities.accounts.AccountBalanceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static java.util.Objects.isNull;

@Service
@Transactional
public class AccountBalanceProcess
{
  @Resource
  private AccountBalanceRepository accountBalanceRepository;

  // TODO: 26.02.2017 implement method 
  public AccountBalance findOrCreateByAccount(Account account){
    AccountBalance accountBalance = accountBalanceRepository.findByAccount(account);
    if (isNull(accountBalance)){
      
    }
    return null;
  }
}
