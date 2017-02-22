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

  public Account findOrCreateByAccountNumber(String accountNumber){

  }
}
