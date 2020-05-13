package com.dev.meetup.controllers;

import com.dev.meetup.enums.EventStatus;
import com.dev.meetup.enums.UserRole;
import com.dev.meetup.models.Event;
import com.dev.meetup.models.SearchItem;
import com.dev.meetup.models.ServerResponse;
import com.dev.meetup.models.User;
import com.dev.meetup.repos.EventRepos;
import com.dev.meetup.repos.UserRepos;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Controller
public class EventController {

    @Autowired
    private EventRepos eventRepos;
    @Autowired
    private UserRepos userRepos;
    private String defFileDir = "/img/";
    private String fileRootDir =  "./src/main/resources/static/img/";
    private ServerResponse response = null;

    @GetMapping("/events")
    public String events(Model model, HttpSession session) {
        List<Event> events = eventRepos.findAll();
        if(events != null) Collections.reverse(events);

        model.addAttribute("events", events);
        return "events";
    }
    @GetMapping("/events/soon")
    public String eventsFuture(Model model) {
        List<Event> eventsSoon = eventRepos.getAllByDateAfterOrderByIdDesc(new Date());

        model.addAttribute("events", eventsSoon);
        return "events";
    }
    @GetMapping("/events/today")
    public String eventsToday(Model model) {
        List<Event> eventsToday = eventRepos.getAllByDateEqualsOrderById(new Date());

        model.addAttribute("events", eventsToday);
        return "events";
    }
    @GetMapping("/events/past")
    public String eventsPast(Model model) {
        List<Event> eventsPast = eventRepos.getAllByDateBeforeOrderByIdDesc(new Date());

        model.addAttribute("events", eventsPast);
        return "events";
    }

    @GetMapping("/event/{id}")
    public String eventDetails(Model model, @PathVariable(value = "id") long id, HttpSession session) {
        if(!eventRepos.existsById(id)){
            model.addAttribute("element", "Event");
            return "not-founded";
        }
        Event event = eventRepos.findById(id);
        Object sessionLogin = session.getAttribute("username");
        boolean isSubscribed;
        //View counter
        if( sessionLogin == null || !sessionLogin.toString().equals(event.getPublisher().getUsername())) {
            event.addView();
            eventRepos.save(event);
        }

        int statusType = event.getDate().compareTo(new Date());
        switch (statusType) {
            case 1: event.setStatus(EventStatus.PREPARE); break;
            case -1: event.setStatus(EventStatus.ENDED); break;
            case 0: event.setStatus(EventStatus.InPROCESS); break;
        }

        isSubscribed = sessionLogin != null && event.getUsers().contains(userRepos.findByUsername(sessionLogin.toString()));
        model.addAttribute("event", event);
        model.addAttribute("isSubscribed", isSubscribed);
        return "event-single";
    }

    @GetMapping("/event/{id}/subscribers")
    public String eventSubscribers(Model model, @PathVariable(value = "id") long id, HttpSession session) {
        if(!eventRepos.existsById(id)){
            model.addAttribute("element", "Event");
            return "not-founded";
        }
        Event event = eventRepos.findById(id);
        Object sessionLogin = session.getAttribute("username");
        if(sessionLogin == null || !sessionLogin.toString().equals(event.getPublisher().getUsername()))
            return "no-permission";
        int statusType = event.getDate().compareTo(new Date());
        switch (statusType) {
            case 1: event.setStatus(EventStatus.PREPARE); break;
            case -1: event.setStatus(EventStatus.ENDED); break;
            case 0: event.setStatus(EventStatus.InPROCESS); break;
        }
        model.addAttribute("event", event);
        return "subscribe-list";
    }

    @PostMapping("/events/subscribe")
    @ResponseBody
    public String eventSubscribe(@RequestParam long id, HttpSession session) {
        if(session.getAttribute("username") == null) {
            response = new ServerResponse("Signup", true);
            return new Gson().toJson(response);
        }
            Event event = eventRepos.findById(id);
            User subscriber = userRepos.findByUsername(session.getAttribute("username").toString());
            String message;
            if(event.getUsers().contains(subscriber)) {

                event.deleteSubscribe(subscriber);
                eventRepos.save(event);
                message = "Unsubscribed";
            } else {
                event.addSubscribe(subscriber);
                eventRepos.save(event);
                message = "Subscribed";

                //sendMail("Привет, вы только что подали заявку на участие в мероприятии <b>" + event.getTitle() + "</b>, его организаторы свяжутся с вами в ближайшее время.", subscriber);
            }
            response = new ServerResponse(message, false);
            return new Gson().toJson(response);
    }

    @PostMapping("/events/delete")
    @ResponseBody
    public String eventDelete(@RequestParam long id) {

        //delete_file(eventRepos.findById(id).getPicture_path());
        eventRepos.deleteById(id);
        response = new ServerResponse("Success", false);
        return new Gson().toJson(response);
    }

    @GetMapping("/events/search")
    @ResponseBody
    public Object[] eventSearch(@RequestParam String match) {

        List<User> users = userRepos.findByUsernameContainsIgnoreCase(match);
        List<Event> events = eventRepos.findByTitleContainsIgnoreCase(match);

        ArrayList<SearchItem> results = new ArrayList<>();
        users.forEach(user -> results.add(new SearchItem(user.getUsername(),"/profile/"+user.getUsername(), user.getAvatar_path(), user.getAbout())));
        events.forEach(event -> {if(event.isVisible()) results.add(new SearchItem(event.getTitle(), "/event/"+event.getId(), event.getPicture_path(), event.getDescription()));});

        //return new Gson().toJson(results);
        return results.toArray();
    }

    @GetMapping("/event/{id}/edit")
    public String eventEdit(Model model, @PathVariable(value = "id") long id, HttpSession session) {

        if(!eventRepos.existsById(id)) return "not-founded";
        Event editEvent = eventRepos.findById(id);
        if(!session.getAttribute("userId").equals(editEvent.getPublisher().getId())) return "no-permission";

        model.addAttribute("event", editEvent);
        model.addAttribute("file", new File(editEvent.getPicture_path()));
        return "events-edit";
    }

    @GetMapping("/events/add")
    public String eventsAdd(HttpSession session) {
        if(session.getAttribute("username") == null) return "redirect:/login";
        return "events-add";
    }
    @PostMapping ("/events/add")
    @ResponseBody
    public String eventsAdd(
            @RequestParam String title, @RequestParam String description, @RequestParam String markup,
            @RequestParam MultipartFile file, @RequestParam String date, @RequestParam String place,
            HttpSession session
    ) {
        try {
            if(eventRepos.findByTitle(title) != null) {
                response = new ServerResponse("Event with this title exists", true);
                return new Gson().toJson(response);
            }

            upload_file(file, fileRootDir);
            User publisher = userRepos.findByUsername(session.getAttribute("username").toString());
            Event event = new Event(title,description,markup,defFileDir + file.getOriginalFilename(), date, publisher, place);
            eventRepos.save(event);

            response = new ServerResponse("Successfully added", false, publisher.getUsername());
            return new Gson().toJson(response);

        } catch (Exception e) {
            response = new ServerResponse("SERVER ERROR => " + Arrays.toString(e.getStackTrace()), true);
            return new Gson().toJson(response);
        }
    }

    @PostMapping ("/events/edit")
    @ResponseBody
    public String eventsEdit(
            @RequestParam String title, @RequestParam String description, @RequestParam String markup,
            //@RequestParam MultipartFile file,
            @RequestParam String dates, @RequestParam String place,
            @RequestParam long editId, HttpSession session
    ) throws ParseException {
//        try {
                Event editEvent = eventRepos.findById(editId);
//                if(eventRepos.findByTitle(title).getId() != editEvent.getId()){
//                    response = new ServerResponse("Event with this title exists", true);
//                    return new Gson().toJson(response);
//                }

                //TO DO edit event picture
                editEvent.Update(title,description,markup, dates, place);
                eventRepos.save(editEvent);
                response = new ServerResponse("Successfully updated", false, session.getAttribute("username").toString());
                return new Gson().toJson(response);
//        } catch (Exception e) {
//            response = new ServerResponse("SERVER ERROR => " + e.getMessage(), true);
//            return new Gson().toJson(response);
//        }
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

    private void delete_file(String path) {
        new File(path).delete();
    }

    private  void sendMail (String text, User user) {
        String to = user.getEmail();         // sender email
        String from = "elsk.kzheup@gmail.com";       // receiver email
        String host = "127.0.0.1";            // mail server host
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "25");
        Session session = Session.getDefaultInstance(properties); // default session
        try {
            MimeMessage message = new MimeMessage(session); // email message
            message.setFrom(new InternetAddress(from)); // setting header fields
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("dev.meetup.subscribe"); // subject line
            // actual mail body
            message.setText(text);
            // Send message
            Transport.send(message);
        } catch (MessagingException mex){ mex.printStackTrace(); }
    }
}

