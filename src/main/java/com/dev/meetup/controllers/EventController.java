package com.dev.meetup.controllers;

import com.dev.meetup.models.Event;
import com.dev.meetup.models.ServerResponse;
import com.dev.meetup.repos.EventRepos;
import com.dev.meetup.repos.UserRepos;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class EventController {

    @Autowired
    private EventRepos eventRepos;
    @Autowired
    private UserRepos userRepos;
    private ServerResponse response = null;
    private String defFileDir = "/img/";
    private String fileRootDir =  "./src/main/resources/static/img/";

    @GetMapping("/events")
    public String events(Model model) {

        Iterable<Event> events = eventRepos.findAll();
        model.addAttribute("events", events);
        return "events";
    }

    @GetMapping("/events/add")
    public String eventsAdd(Model model, HttpSession session) {

        if(session.getAttribute("username") == null) return "redirect:/login";
        return "events-add";
    }

    @PostMapping ("/events/add")
    @ResponseBody
    public String eventsAdd(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String markup,
            @RequestParam MultipartFile file,
            @RequestParam String dates,
            HttpSession session
    ) {

        try {
            if(eventRepos.findByTitle(title) != null) {
                response = new ServerResponse("Event with this title exists", true);
                return new Gson().toJson(response);
            }

            Event event = new Event(title,description,markup, getUpladedFilePath(file), dates, userRepos.findByUsername(session.getAttribute("username").toString()));
            eventRepos.save(event);

            response = new ServerResponse("Successfully added", false, (int)eventRepos.findByTitle(title).getId());
            return new Gson().toJson(response);

        } catch (Exception e) {
            response = new ServerResponse("SERVER ERROR => " + e.getMessage(), true);
            return new Gson().toJson(response);
        }
    }

    private String getUpladedFilePath(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();

        File dir = new File(fileRootDir);
        File uploadedFile = new File(dir.getAbsolutePath() + File.separator +file.getOriginalFilename());
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
        stream.write(bytes);
        stream.flush();
        stream.close();

        return defFileDir + file.getOriginalFilename();
    }
}

