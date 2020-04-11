package com.dev.meetup.controllers;

import com.dev.meetup.models.User;
import com.dev.meetup.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ProfileController {

    @Autowired
    private UserRepos userRepos;

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {

        if(session.getAttribute("username") == null) return "redirect:/login";

        User user = userRepos.findByUsername(session.getAttribute("username").toString());
        model.addAttribute("user", user);
        return "profile";
    }
}
