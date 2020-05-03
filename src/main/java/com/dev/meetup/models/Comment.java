package com.dev.meetup.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    private Event event;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "comment_reply",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "reply_id"))
    private Set<Comment> commentsReply= new HashSet<>();

    private String text;
    private Date publishDate;

    public Comment() {}

    public Comment(Event event, User user, String text) {
        this.event = event;
        this.user = user;
        this.text = text;
        this.publishDate = new Date();
    }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Set<Comment> getCommentsReply() { return commentsReply; }
    public void setCommentsReply(Set<Comment> commentsReply) { this.commentsReply = commentsReply; }

    public String getStringDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(publishDate);
    }
}
