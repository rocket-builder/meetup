package com.dev.meetup.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="chat_id", nullable=false)
    private Chat chat;
    @ManyToOne
    @JoinColumn(name="from_id", nullable=false)
    private User from;
    @ManyToOne
    @JoinColumn(name="to_id", nullable=false)
    private User to;

    private String message;
    private Date date;

    public Message(User from, User to, String message, Date date) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    private Chat getChat() { return chat; }
    public long getId() { return id; }

    public User getFrom() { return from; }
    public User getTo() {return to; }

    @Override
    public String toString() {
        return "Message [chatId=" + getChat().getId()+ ", message=" + message + "]";
    }

    public Object getText() {
        return message;
    }
}
