package com.dev.meetup.controllers;

import com.dev.meetup.Security;
import com.dev.meetup.enums.UserRole;
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
public class LoginController {

    @Autowired
    private UserRepos userRepos;
    private ServerResponse response = null;

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/events";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session
    ) {
        User user = userRepos.findByUsername(username);
        if(user == null) {

            response = new ServerResponse("User not found", true);
            return new Gson().toJson(response);
        } else
            if(!user.getPassword().equals(Security.MD5(password))) {

                response = new ServerResponse("Incorrect password", true);
                return new Gson().toJson(response);
            } else {

                session.setAttribute("username", username);
                session.setAttribute("userId", user.getId());
                session.setAttribute("isAdmin", user.getRole().equals(UserRole.ADMIN)? true : null);
                if(user.getAvatar_path() != null) {
                    session.setAttribute("avatar", user.getAvatar_path());
                }
            }

        response = new ServerResponse("Success", false, username);
        return new Gson().toJson(response);
    }
}
