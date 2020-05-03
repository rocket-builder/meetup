package com.dev.meetup.controllers;

import com.dev.meetup.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;


@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Hello, Rocket!");
        return "not-founded";
    }

    @GetMapping("/index")
    public String chat(Model model) {
        return "index";
    }

}
