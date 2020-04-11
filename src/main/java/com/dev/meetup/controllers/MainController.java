package com.dev.meetup.controllers;

import com.dev.meetup.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
/* import org.springframework.web.bind.annotation.RequestParam; */

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Hello, Rocket!");
        return "home";
    }

}
