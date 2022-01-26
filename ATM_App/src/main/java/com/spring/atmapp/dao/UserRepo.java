package com.spring.atmapp.dao;

import com.spring.atmapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
	User findUserByEmail(String email);
	boolean existsByEmail(String email);
}
