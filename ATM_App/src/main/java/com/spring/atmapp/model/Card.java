package com.spring.atmapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;


//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Card {

    @Id
    @Column(unique = true)
    @Max(value = 9999, message = "Please enter the card no in 4 digit")
    @Min(value = 1000, message = "Please enter the card no in 4 digit")
    private int no;

    @Pattern(regexp = "[a-zA-Z][a-zA-Z\\s]+", message = "Please enter a valid Bank Name")
    private String bank;

    private int bal;

    private boolean isActive;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    private List<Statement> statements = new ArrayList<>();

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public int getBal() {
		return bal;
	}

	public void setBal(int bal) {
		this.bal = bal;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	public void setStatements(List<Statement> statements) {
		this.statements = statements;
	}

	public Card(
			@Max(value = 9999, message = "Please enter the card no in 4 digit") @Min(value = 1000, message = "Please enter the card no in 4 digit") int no,
			@Pattern(regexp = "[a-zA-Z][a-zA-Z\\s]+", message = "Please enter a valid Bank Name") String bank, int bal,
			boolean isActive, User user, List<Statement> statements) {
		super();
		this.no = no;
		this.bank = bank;
		this.bal = bal;
		this.isActive = isActive;
		this.user = user;
		this.statements = statements;
	}

	public Card() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Card [no=" + no + ", bank=" + bank + ", bal=" + bal + ", isActive=" + isActive + ", user=" + user
				+ ", statements=" + statements + "]";
	}
    
    

}
