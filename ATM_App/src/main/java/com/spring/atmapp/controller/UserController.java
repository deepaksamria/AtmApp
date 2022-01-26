package com.spring.atmapp.controller;

import com.spring.atmapp.model.Card;
import com.spring.atmapp.model.User;
import com.spring.atmapp.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    public Services services;

    @RequestMapping(value = "/user-home")
    public String userHome(HttpSession session, Model m) {
        Object email = session.getAttribute("email");
        
        if (email == null) {
            return "signin";
        } else {
        	try {
        	String userEmail = email.toString();
        	List<Card> cards = services.getCards(userEmail);
			m.addAttribute("userCard", cards);
			return "user-home";
        	}catch (Exception e) {
        		return "signin";
        	}
		}
        
    }

    @RequestMapping("/update_profile")
    public String update_profile(Model m, HttpSession session) {
        Object email = session.getAttribute("email");
        if (email == null) {
            return "signin";
        } else {
            String tempemail = email.toString();

            User user = this.services.getUser(tempemail);
            m.addAttribute("user", user);
            return "update_user";
        }
    }

    @RequestMapping(value = "/updateuserprofile", method = RequestMethod.POST)
    public String updateuserprofile(@Valid @ModelAttribute("user") User user, BindingResult result, Model m, HttpSession session) {
//	Object email = session.getAttribute("email");
//	if(email==null) {
//		return "signin";
//	} 
        if (result.hasErrors()) {
            session.setAttribute("message", "Please fill the form properly");
            m.addAttribute("user", user);
            return "update_user";
        } else {
            String email = (String) session.getAttribute("email");
            boolean userupdate = this.services.updateUser(user, email);
            if (userupdate) {
                session.setAttribute("message", "User updated sucessfully!!!!");
                return "update_user";
            } else
                session.setAttribute("message", "Something went wrong... Pleasery again later!!!");
            m.addAttribute("user", user);
            return "update_user";
        }
    }
    
    

}




