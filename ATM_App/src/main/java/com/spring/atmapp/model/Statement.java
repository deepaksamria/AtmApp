package com.spring.atmapp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

@Entity
public class Statement {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Date date;
	
	private String particulars;
	
	private int dr;
	
	private int cr;
	
	private int balance;
	
	@ManyToOne
	private Card card;
	
	public Statement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Statement [id=" + id + ", date=" + date + ", particulars=" + particulars + ", dr=" + dr + ", cr=" + cr
				+ ", balance=" + balance + ", card=" + card + "]";
	}
	
	
	public Statement(Integer id, Date date, String particulars, int dr, int cr, int balance, Card card) {
		super();
		this.id = id;
		this.date = date;
		this.particulars = particulars;
		this.dr = dr;
		this.cr = cr;
		this.balance = balance;
		this.card = card;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public int getDr() {
		return dr;
	}
	public void setDr(int dr) {
		this.dr = dr;
	}
	public int getCr() {
		return cr;
	}
	public void setCr(int cr) {
		this.cr = cr;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	
	
}
