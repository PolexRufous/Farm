package com.farm.database.entities.accounts;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository  extends CrudRepository<Account, Long>{

  Account findByAccountNumber(String accountNumber);

  Account findByPartnerIdAndAccountType(Long partnerId, AccountType accountType);
}
