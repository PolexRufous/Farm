package com.farm.processes;

import com.farm.database.accounts.AccountBalance;
import com.farm.database.accounts.AccountBalanceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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

  public AccountBalance findOrCreateByAccountNumber(){

  }
}
