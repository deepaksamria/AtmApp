package com.spring.atmapp.service;

import com.spring.atmapp.dao.CardRepo;
import com.spring.atmapp.dao.StatementRepo;
import com.spring.atmapp.dao.UserRepo;
import com.spring.atmapp.model.Card;
import com.spring.atmapp.model.Statement;
import com.spring.atmapp.model.User;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
//@RequiredArgsConstructor
public class Services {
	@Autowired
   public UserRepo userRepo;
	@Autowired
   public CardRepo cardRepo;
	@Autowired
	public StatementRepo stRepo;

    public int saveUser(User user) {
        user.setActive(true);
        User u = userRepo.save(user);
        return u.getId();
    }

    public String verifyLogin(String email, String pass) {
        final String password;
        final boolean userExists = userRepo.existsByEmail(email);
        if (userExists) {
            User tempUser = userRepo.findUserByEmail(email);
            password = tempUser.getPass();
            if (password.equals(pass)) {
                return email;
            } else
                return null;
        } else
            return null;
    }

    public User getUser(String tempEmail) {
        return userRepo.findUserByEmail(tempEmail);
    }

    public boolean updateUser(User user, String email) {
        User tempUser = userRepo.findUserByEmail(email);
        String name = user.getName();
        tempUser.setName(name);
        String pass = user.getPass();
        tempUser.setPass(pass);
        String mob = user.getMob();
        tempUser.setMob(mob);
        String city = user.getCity();
        tempUser.setCity(city);
        try {
            userRepo.save(tempUser);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean saveCard(Card card, String email) {
        User temp = this.userRepo.findUserByEmail(email);
        List<Card> list=new ArrayList<>();
        card.setActive(true);
        card.setBal(0);
        card.setUser(temp);
        list.add(card);
        temp.setCard(list);
        try {
            cardRepo.save(card);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Card> getCards(String email) {
        User temp = userRepo.findUserByEmail(email);
        int id = temp.getId();
        try{
        	List<Card> list = cardRepo.findByUserId(id);
        	return list;
        }catch (Exception e) {
        	return null;
        }      
    }

	public boolean addMoney(Integer cardNum, Integer amt, String email) {
		Date today=new Date();
		User user = userRepo.findUserByEmail(email);
		Card c = cardRepo.findCardByNo(cardNum);
		int oldbal=c.getBal();
		int newbal=oldbal+amt;
		c.setBal(newbal);
		Statement s=new Statement();
		s.setDate(today);
		s.setParticulars("Amount added by "+user.getName());
		s.setCr(amt);
		s.setCard(c);
		try {
		cardRepo.save(c);
		stRepo.save(s);	
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	public Integer getBal(Integer cardNum) {
		Card card = cardRepo.findCardByNo(cardNum);
		Integer bal=card.getBal();
		return bal;
	}

	public boolean withdrawMoney(Integer cardNum, Integer amt, String email) {
		Date today=new Date();
		User user = userRepo.findUserByEmail(email);
		Card c = cardRepo.findCardByNo(cardNum);
		int oldbal=c.getBal();
		int newbal=oldbal-amt;
		c.setBal(newbal);
		Statement s=new Statement();
		s.setDate(today);
		s.setParticulars("Amount withdraw by "+user.getName());
		s.setDr(amt);
		s.setCard(c);
		try {
		cardRepo.save(c);
		stRepo.save(s);	
		}catch(Exception e) {
			return false;
		}
		return true;
		
	}

	public boolean verifyCard(Integer rCard) {
		Card c = cardRepo.findCardByNo(rCard);
		if(c==null) {
			return false;
		}else
			return true;
	}

	public boolean transferMoney(Integer cardNum, Integer rCard, Integer amt, String email) {
		Date today=new Date();
		User user = userRepo.findUserByEmail(email);
		Card recCard = cardRepo.findCardByNo(rCard);
		Card senCard = cardRepo.findCardByNo(cardNum);
		//Sender Card Transaction
		int senCardOldBal=senCard.getBal();
		int senCardNewBal=senCardOldBal-amt;
		senCard.setBal(senCardNewBal);
		Statement s=new Statement();
		s.setDate(today);
		s.setParticulars("Amount transferred to card no "+recCard.getNo());
		s.setDr(amt);
		s.setCard(senCard);
		//Rec Card Transaction
		int recCardOldBal=recCard.getBal();
		int recCardNewBal=recCardOldBal+amt;
		recCard.setBal(recCardNewBal);
		Statement s2=new Statement();
		s2.setDate(today);
		s2.setParticulars("Amount transferred by "+user.getName());
		s2.setCr(amt);
		s2.setCard(recCard);
		try {
		cardRepo.save(senCard);
		stRepo.save(s);	
		cardRepo.save(recCard);
		stRepo.save(s2);	
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	public List<Statement> getStatement(Integer no) {
		List<Statement> statement = stRepo.findByCardNo(no);
		return statement;	
	}

	public Card getCard(Integer no) {
		Card c=cardRepo.findCardByNo(no);
		return c;
	}
}
