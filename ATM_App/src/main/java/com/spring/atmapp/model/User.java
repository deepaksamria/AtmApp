package com.spring.atmapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Pattern(regexp = "[a-zA-Z][a-zA-Z\\s]+", message = "Please enter a valid name.")
    private String name;

    @Column(unique = true)
    @Pattern(regexp = "[a-zA-Z0-9][a-zA-Z0-9_.]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+", message = "Please enter a valid email.")
    private String email;

    @Size(min = 4, max = 16, message = "Password should be between 4-16 charactars")
    private String pass;

    @Pattern(regexp = "[6789][0-9]{9}", message = "Please enter a valid mob number.")
    private String mob;

    @Pattern(regexp = "[a-zA-Z][a-zA-Z\\s]+", message = "Please enter a valid city.")
    private String city;

    private boolean isActive;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Card> card = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<Card> getCard() {
		return card;
	}

	public void setCard(List<Card> card) {
		this.card = card;
	}

	public User(Integer id,
			@Pattern(regexp = "[a-zA-Z][a-zA-Z\\s]+", message = "Please enter a valid name.") String name,
			@Pattern(regexp = "[a-zA-Z0-9][a-zA-Z0-9_.]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+", message = "Please enter a valid email.") String email,
			@Size(min = 4, max = 16, message = "Password should be between 4-16 charactars") String pass,
			@Pattern(regexp = "[6789][0-9]{9}", message = "Please enter a valid mob number.") String mob,
			@Pattern(regexp = "[a-zA-Z][a-zA-Z\\s]+", message = "Please enter a valid city.") String city,
			boolean isActive, List<Card> card) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.mob = mob;
		this.city = city;
		this.isActive = isActive;
		this.card = card;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", pass=" + pass + ", mob=" + mob + ", city="
				+ city + ", isActive=" + isActive + ", card=" + card + "]";
	}
    
    

}
