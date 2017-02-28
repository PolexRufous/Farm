package com.farm.database.accounts;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository  extends CrudRepository<Account, Long>{

  Account findByAccountNumber(String accountNumber);
}
