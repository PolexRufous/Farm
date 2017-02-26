package com.farm.processes;

import com.farm.database.accounts.Account;
import com.farm.database.accounts.AccountBalance;
import com.farm.database.accounts.AccountBalanceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static java.util.Objects.isNull;

/**
 * Farm project. 2017
 * Description:
 */
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
