package com.tacos.tacocloud_nosql.repository;

import com.tacos.tacocloud_nosql.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

}
