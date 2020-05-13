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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ModerationController {

    @Autowired
    private UserRepos userRepos;
    @Autowired
    private EventRepos eventRepos;
    private ServerResponse response = null;

    @GetMapping("/events/moderation")
    public String moderation(Model model, HttpSession session) {
        if(session.getAttribute("isAdmin") == null)
            return "no-permission";

        List<Event> events = eventRepos.findAllByIsVisibleFalseOrderByIdDesc();
        model.addAttribute("events", events);
        return "moderation";
    }

    @PostMapping("/moderation/approve")
    @ResponseBody
    public String approve(@RequestParam long id) {

        Event event = eventRepos.findById(id);
        event.setVisible(true);
        eventRepos.save(event);
        return "Approved";
    }

    @PostMapping("/moderation/deny")
    @ResponseBody
    public String deny(@RequestParam long id) {

        eventRepos.deleteById(id);
        return "Deleted";
    }
}
