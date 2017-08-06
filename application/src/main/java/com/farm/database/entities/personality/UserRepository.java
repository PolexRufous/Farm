package com.farm.database.entities.personality;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    User findById(Long id);
    User findByLogin(String login);
}
