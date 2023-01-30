package com.example.block_4.controller;


import com.example.block_4.database.model.Person;
import com.example.block_4.security.LoginForm;
import com.example.block_4.service.impl.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.NoSuchElementException;

@Controller
public class PageController {
    @Autowired
    private PersonServiceImpl personService;

    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String post(@ModelAttribute("loginForm") LoginForm loginForm, Model model, HttpServletResponse response) throws IOException {
        Person person;
        try {
            person = personService.getByEmail(loginForm.getEmail());
        }catch(NoSuchElementException e){
            model.addAttribute("message", "Email is not valid");
            return login();
        }

        boolean password = new BCryptPasswordEncoder().matches(loginForm.getPassword(), person.getPassword());
        String email = loginForm.getEmail();

        if(password && email.equals(person.getEmail())) {
            session.setAttribute("login", loginForm.getEmail());
            session.setAttribute("password", loginForm.getPassword());
            session.setAttribute("userName", person.getName());

            model.addAttribute("person", person.mapToDto());
            response.sendRedirect(account());
            return account();
        }

        model.addAttribute("message", "Password is not valid");
        return login();
    }
    
    @GetMapping({"/dashboard", "/"})
    public String account(){
        return "dashboard";
    }

    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("users", personService.getAllDto());
        return "users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
}
