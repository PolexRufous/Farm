package com.farm.database.entities.accounts;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository  extends CrudRepository<Account, Long>{

  Account findByAccountNumber(String accountNumber);
  List<Account> findAllByPartnerIdAndAccountType(Long partnerId, AccountType accountType);
}
