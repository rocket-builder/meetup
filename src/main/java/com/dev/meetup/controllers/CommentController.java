package com.dev.meetup.controllers;

import com.dev.meetup.models.Comment;
import com.dev.meetup.models.Event;
import com.dev.meetup.models.ServerResponse;
import com.dev.meetup.models.User;
import com.dev.meetup.repos.CommentRepos;
import com.dev.meetup.repos.EventRepos;
import com.dev.meetup.repos.UserRepos;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentRepos commentRepos;
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private EventRepos eventRepos;
    private ServerResponse response = null;

    @PostMapping("/comment/add")
    @ResponseBody
    public String addComment(@RequestParam("text") String text, @RequestParam("eventId") long eventId, HttpSession session) {

        User user = userRepos.findByUsername(session.getAttribute("username").toString());
        Event event = eventRepos.findById(eventId);

        Comment comment = new Comment(event, user, text);
        commentRepos.save(comment);

        response = new ServerResponse("Success", false, user.getUsername(), user.getAvatar_path());
        return new Gson().toJson(response);
    }

    @PostMapping("/comment/{id}/reply")
    @ResponseBody
    public String addReply(@RequestParam("text") String text, @PathVariable("id") long id, HttpSession session) {

        return "success";
    }
}
