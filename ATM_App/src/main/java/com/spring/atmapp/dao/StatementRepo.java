package com.spring.atmapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.atmapp.model.Statement;
import com.spring.atmapp.model.User;
@Repository
public interface StatementRepo extends CrudRepository<Statement, Integer> {
	
	public List<Statement> findByCardNo(Integer CardNo);
	
}
