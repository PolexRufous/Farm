package com.farm.database.entities.accounts;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository  extends CrudRepository<AccountEntity, Long>{

  AccountEntity findByAccountNumber(String accountNumber);

  AccountEntity findByPartnerIdAndAccountType(Long partnerId, AccountType accountType);
}
