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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    public Services services;

    @RequestMapping("/")
    public String home(Model m) {
        m.addAttribute("title", "Homepage - Welcome");
        return "home";
    }

    @RequestMapping("/signup")
    public String signup(Model m) {

        m.addAttribute("title", "Homepage - Sign-up page");
        m.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model m, HttpSession session) {

        if (result.hasErrors()) {
            session.setAttribute("message", "Please fill the form properly");
            m.addAttribute("user", user);
            return "signup";
        }

        int id = services.saveUser(user);
        session.setAttribute("message", "Registration successful!!!!, Your User Id " + id + " Please log-in");
        return "signup";
    }

    @RequestMapping(value = "/signin")
    public String signin() {
        return "signin";
    }

    @RequestMapping(value = "/signinprocess", method = RequestMethod.POST)
    public String signinprocess(@RequestParam("email") String email, @RequestParam("pass") String pass, HttpSession session, Model m) {

        String vrifiedemail = services.verifyLogin(email, pass);
        if (vrifiedemail != null) {
            session.setAttribute("email", vrifiedemail);
            List<Card> card = services.getCards(vrifiedemail);
            m.addAttribute("userCard", card);
            return "user-home";
        } else
            session.setAttribute("message", "Invalid credentials... Please log-in with valid credentials.");
        return "signin";
    }

    @RequestMapping("/log-out")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        return "home";
    }
    
}
