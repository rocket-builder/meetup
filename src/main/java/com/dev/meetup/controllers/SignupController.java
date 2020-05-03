package com.dev.meetup.controllers;

import com.dev.meetup.Security;
import com.dev.meetup.models.ServerResponse;
import com.dev.meetup.models.User;
import com.dev.meetup.repos.UserRepos;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private UserRepos userRepos;
    private ServerResponse response = null;

    @GetMapping
    public String signup() {
        return "signup";
    }

    @PostMapping
    @ResponseBody
    public String signup(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session
    ) {
        User userFromDb = userRepos.findByUsername(username);

        if(userFromDb != null) {

            response = new ServerResponse("This username are used, try another", true);
            return new Gson().toJson(response);
        } else {
            User user = new User(username, email, Security.MD5(password));
            userRepos.save(user);

            session.setAttribute("username", username);
            response = new ServerResponse("", false, username);
            return new Gson().toJson(response);
        }
    }
}


