package com.dev.meetup.controllers;

import com.dev.meetup.enums.EventStatus;
import com.dev.meetup.models.*;
import com.dev.meetup.repos.ChatRepos;
import com.dev.meetup.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@CrossOrigin
public class ChatController {

//    @Autowired
//    private UserRepos userRepos;
//    @Autowired
//    private ChatRepos chatRepos;
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping("/secured/room")
//    public void sendSpecific(
//            @Payload WebSocketMessage msg,
//            Principal user,
//            @Header("simpSessionId") String sessionId) throws Exception {
//        WebSocketOutputMessage out = new WebSocketOutputMessage(
//                msg.getFrom(),
//                msg.getText(),
//                new SimpleDateFormat("HH:mm").format(new Date()));
//        simpMessagingTemplate.convertAndSendToUser(
//                msg.getTo(), "/secured/user/queue/specific-user", out);
//    }

//    @GetMapping("/chat/{fromId}_{toId}")
//    public String chat(@PathVariable(value = "fromId") long fromId, @PathVariable(value = "toId") long toId, Model model) {
//        User from = userRepos.findById(fromId);
//        User to = userRepos.findById(toId);
//
//        Chat chatFrom = chatRepos.findChatByFromAndTo(from, to);
//        Chat chatTo = chatRepos.findChatByFromAndTo(to, from);
//        if(chatFrom == null) {
//
//            chatFrom = new Chat(from, to);
//            chatRepos.save(chatFrom);
//        } else if (chatTo == null) {
//
//            chatTo = new Chat(to, from);
//            chatRepos.save(chatTo);
//        }
//
//        model.addAttribute("chat", chatFrom);
//        return "chat";
//    }
}
