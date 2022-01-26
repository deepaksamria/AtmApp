package com.spring.atmapp.dao;

import com.spring.atmapp.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CardRepo extends JpaRepository<Card, Integer> {
	
    List<Card> findByUserId(Integer userId);
    Card findCardByNo(Integer no);

}
//@Query(value="select * from card", nativeQuery = true)