package com.farm.database.processes;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.accounts.AccountRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Farm project. 2017
 * Description:
 */
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
