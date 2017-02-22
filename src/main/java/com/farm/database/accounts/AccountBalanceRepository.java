package com.farm.database.accounts;

import org.springframework.data.repository.CrudRepository;

public interface AccountBalanceRepository extends CrudRepository<AccountBalance, Long> {

  AccountBalance findByAccount(Account account);
}
