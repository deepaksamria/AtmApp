package com.spring.atmapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.spring.atmapp.model.Card;
import com.spring.atmapp.model.Statement;
import com.spring.atmapp.model.User;
import com.spring.atmapp.service.Services;

@Controller
public class Card_Cantroller {
	@Autowired
	public Services services;
	
	
	@RequestMapping(value="/registercard")
	public String signin(HttpSession session, Model m) {
		Object email = session.getAttribute("email");
		if(email==null) {
			return "signin";
		} else
		m.addAttribute("card", new Card());
		return "cardregistration";
		
	}
	
	@RequestMapping(value="/cr_process", method=RequestMethod.POST)
	public String cr_process(@Valid @ModelAttribute("card") Card card, BindingResult result, Model m, HttpSession session) {
		Object email = session.getAttribute("email");
		if(email==null) {
			return "signin";
		}
		if(result.hasErrors()) {
			session.setAttribute("message", "Please fill the form properly");
			m.addAttribute("card", card);
			return "cardregistration";
		}else
		{
			String uemail = email.toString();
			boolean saveCard = this.services.saveCard(card, uemail);
			if(saveCard) {
				session.setAttribute("message", "Card saved sucessfully!!!!!");
				return "cardregistration";
			}
			else
				session.setAttribute("message", "Something went wrong, please try later");
				return "cardregistration";
		}
	}
	@RequestMapping(value="/addMoney/{no}", method = RequestMethod.GET)
	public String addMoney(@PathVariable("no") Integer no, HttpSession session) {
		Object email = session.getAttribute("email");
		if(email==null) {
			return "signin";
		}else
			session.setAttribute("cardNo", no);
			return "addMoney";
	}
	@RequestMapping(value="/addMoneyProcess", method = RequestMethod.POST)
	public String addMoneyProcess(@RequestParam("amount") String amount, @RequestParam("pass") String pass, HttpSession session) {
		Integer amt;
		String email = (String) session.getAttribute("email");
		try {
		amt=Integer.parseInt(amount);
		}catch(Exception e) {
			session.setAttribute("message", "Please enter a valid amount");
			return "addMoney";
		}
		if(amt>=10000 || amt<0) {
			session.setAttribute("message", "Amount should be between 1 to 10,000 only");
			return "addMoney";
		}
		String verifyPass=services.verifyLogin(email, pass);
		if(verifyPass==null) {
			session.setAttribute("message", "Password incorrect, please enter correct password");
			return "addMoney";
		}
		Object no = session.getAttribute("cardNo");
		String cardNo=no.toString();
		Integer cardNum=Integer.parseInt(cardNo);
		boolean status = services.addMoney(cardNum, amt, email);
		if(status) {
			session.setAttribute("message", "Mondey added sucessfully!!!!");
			session.removeAttribute(cardNo);
			return "addMoney";
		}else
		session.setAttribute("message", "Something went wrong... Please try again later");
		return "addMoney";
	}
	
	@RequestMapping(value="/withdrawMoney/{no}", method = RequestMethod.GET)
	public String withdrawMoney(@PathVariable("no") Integer no,HttpSession session) {
		Object email = session.getAttribute("email");
		if(email==null) {
			return "signin";
		}else
			session.setAttribute("cardNo", no);
			return "withdrawMoney";
	}
	
	
	@RequestMapping(value="/withdrawMoneyProcess", method = RequestMethod.POST)
	public String withdrawMoneyProcess(@RequestParam("amount") String amount, @RequestParam("pass") String pass, HttpSession session) {
		Integer amt;
		String email = (String) session.getAttribute("email");
		try {
		amt=Integer.parseInt(amount);
		}catch(Exception e) {
			session.setAttribute("message", "Please enter a valid amount");
			return "withdrawMoney";
		}
		if(amt>=10000 || amt<0) {
			session.setAttribute("message", "Amount should be between 1 to 10,000 only");
			return "withdrawMoney";
		}
		Object no = session.getAttribute("cardNo");
		String cardNo=no.toString();
		Integer cardNum=Integer.parseInt(cardNo);
		Integer bal=services.getBal(cardNum);
		if(amt>bal) {
			session.setAttribute("message", "Insuficient funds, please enter amount less than or equal to "+bal);
			return "withdrawMoney";
		}
		boolean status = services.withdrawMoney(cardNum, amt, email);
		if(status) {
			session.setAttribute("message", "Mondey with-drawn sucessfully!!!!");
			session.removeAttribute(cardNo);
			return "withdrawMoney";
		}else
		session.setAttribute("message", "Something went wrong... Please try again later");
		return "withdrawMoney";
	}
	
	@RequestMapping(value = "/transferMoney/{no}", method = RequestMethod.GET)
	public String transferMoney(@PathVariable("no") Integer no, HttpSession session) {
		Object email = session.getAttribute("email");
		if(email==null) {
			return "signin";
		}else
			session.setAttribute("cardNo", no);
			return "transferMoney";
	}
	@RequestMapping(value = "/transferMoneyProcess", method = RequestMethod.POST)
	public String transferMoneyProcess(@RequestParam("rCard") Integer rCard, @RequestParam("amount") String amount, @RequestParam("pass") String pass, HttpSession session) {
		boolean verifyCard = services.verifyCard(rCard);
		if(!verifyCard) {
			session.setAttribute("message", "Receiver Card not found, please try again with correct card");
			return "transferMoney";
		}
		Integer amt;
		String email = (String) session.getAttribute("email");
		try {
		amt=Integer.parseInt(amount);
		}catch(Exception e) {
			session.setAttribute("message", "Please enter a valid amount");
			return "transferMoney";
		}
		if(amt>=10000 || amt<0) {
			session.setAttribute("message", "Amount should be between 1 to 10,000 only");
			return "transferMoney";
		}
		Object no = session.getAttribute("cardNo");
		String cardNo=no.toString();
		Integer cardNum=Integer.parseInt(cardNo);
		Integer bal=services.getBal(cardNum);
		if(amt>bal) {
			session.setAttribute("message", "Insuficient funds, please enter amount less than or equal to "+bal);
			return "transferMoney";
		}
		boolean status = services.transferMoney(cardNum, rCard, amt, email);
		if(status) {
			session.setAttribute("message", "Money transferred sucessfully!!!!");
			session.removeAttribute(cardNo);
			return "transferMoney";
		}else
		session.setAttribute("message", "Something went wrong... Please try again later");
		return "transferMoney";	
	}
	@RequestMapping(value = "/transactionHistory/{no}", method = RequestMethod.GET)
	public String transactionHistory(@PathVariable("no") Integer no, HttpSession session, Model m) {
		Object email = session.getAttribute("email");
		String uemail = email.toString();
		if(email==null) {
			return "signin";
		} else {
			List<Statement> st = services.getStatement(no);
			Card card = services.getCard(no);
			User user = services.getUser(uemail);
			m.addAttribute("st",st);
			m.addAttribute("card", card);
			m.addAttribute("user", user);
			return "tHistory";
		}
			
	}
}	


