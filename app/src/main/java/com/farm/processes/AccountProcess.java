package com.farm.processes;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.farm.database.accounts.Account;
import com.farm.database.accounts.AccountRepository;

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
