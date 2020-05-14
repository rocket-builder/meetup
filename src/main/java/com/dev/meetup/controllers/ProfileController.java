package com.dev.meetup.controllers;

import com.dev.meetup.models.Event;
import com.dev.meetup.models.ServerResponse;
import com.dev.meetup.models.User;
import com.dev.meetup.repos.EventRepos;
import com.dev.meetup.repos.UserRepos;
import com.dev.meetup.Security;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


@Controller
public class ProfileController {

    @Autowired
    private UserRepos userRepos;
    @Autowired
    private EventRepos eventRepos;

    private ServerResponse response;
    private String defFileDir = "/img/profile/";
    private String fileRootDir =  "./src/main/resources/static/img/profile/";

    @GetMapping("/profile/{username}")
    public String profile(Model model, @PathVariable String username, HttpSession session) {

        User user = userRepos.findByUsername(username);
        if(user == null)
        {
            model.addAttribute("element", "User " + username);
            return "not-founded";
        }

        List<Event> eventsByUser = eventRepos.findAllByPublisherOrderByIdDesc(user);
        model.addAttribute("user", user);
        model.addAttribute("eventsByUser", eventsByUser);
        model.addAttribute("isProfileOwner",
                session.getAttribute("username") != null &&
                        username.equals(session.getAttribute("username").toString())
        );
        return "profile";
    }

    @PostMapping("/profile/edit/save")
    @ResponseBody
    public String profileEditSave(@RequestParam String username, @RequestParam String about, HttpSession session) {

        String sessionUsername = session.getAttribute("username").toString();
        if(userRepos.findByUsername(username) != null && !userRepos.findByUsername(username).getUsername().equals(sessionUsername)) {
            response = new ServerResponse("Username already taken", true);
            return new Gson().toJson(response);
        }

        User user = userRepos.findByUsername(sessionUsername);
        user.setUsername(username);
        user.setAbout(about);
        userRepos.save(user);

        session.setAttribute("username", username);
        response = new ServerResponse("Saved", false);
        return new Gson().toJson(response);
    }
    @PostMapping("/profile/avatar/save")
    @ResponseBody
    public String profileAvatarSave(@RequestParam MultipartFile file, HttpSession session) throws IOException {

        upload_file(file, fileRootDir);
        User user = userRepos.findByUsername(session.getAttribute("username").toString());
        user.setAvatar_path(defFileDir + file.getOriginalFilename());
        userRepos.save(user);

        return "Success";
    }
    @PostMapping("/profile/background/save")
    @ResponseBody
    public String profileBackgroundSave(@RequestParam MultipartFile file, HttpSession session) throws IOException {

        upload_file(file, fileRootDir);
        User user = userRepos.findByUsername(session.getAttribute("username").toString());
        user.setBackground_path(defFileDir + file.getOriginalFilename());
        userRepos.save(user);

        return "Success";
    }
    @PostMapping("/profile/email/save")
    @ResponseBody
    public String profileEmailSave(@RequestParam String email, HttpSession session) {

        User user = userRepos.findByUsername(session.getAttribute("username").toString());
        user.setEmail(email);
        userRepos.save(user);

        return "Success";
    }

    @PostMapping("/profile/password/save")
    @ResponseBody
    public String profileEmailSave(@RequestParam String oldPassword, @RequestParam String newPassword, HttpSession session) {

        User user = userRepos.findByUsername(session.getAttribute("username").toString());

        if(!user.getPassword().equals(Security.MD5(oldPassword))) {
            response = new ServerResponse("Incorrect password", true);
            return new Gson().toJson(response);
        }
        user.setPassword(Security.MD5(newPassword));
        userRepos.save(user);

        response = new ServerResponse("Password updated", false);
        return new Gson().toJson(response);
    }


    @GetMapping("/profile/{username}/settings")
    public String profileSettings(Model model, @PathVariable String username, HttpSession session) {
        Object sessionUserLogin = session.getAttribute("username");
        if(sessionUserLogin == null || !username.equals(sessionUserLogin.toString())) {
            return "no-permission";
        }
        User user = userRepos.findByUsername(username);
        model.addAttribute("user", user);
        return "profile-settings";
    }
    @GetMapping("/profile/{username}/edit")
    public String profileEdit(Model model, @PathVariable String username, HttpSession session) {
        Object sessionUserLogin = session.getAttribute("username");
        if(sessionUserLogin == null || !username.equals(sessionUserLogin.toString())) {
            return "no-permission";
        }
        User user = userRepos.findByUsername(username);
        model.addAttribute("user", user);
        return "profile-edit";
    }

    @PostMapping("/profile/delete")
    @ResponseBody
    public String profileDelete(@RequestParam boolean approve, HttpSession session) {
        if(approve) {
            User user = userRepos.findByUsername(session.getAttribute("username").toString());
            userRepos.delete(user);

            session.invalidate();
            return "Profile deleted";
        }
        return "Approve is false";
    }

    private void upload_file(MultipartFile file, String path) throws IOException {
        byte[] bytes = file.getBytes();

        File dir = new File(path);
        File uploadedFile = new File(dir.getAbsolutePath() + File.separator +file.getOriginalFilename());
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
        stream.write(bytes);
        stream.flush();
        stream.close();
    }
}
