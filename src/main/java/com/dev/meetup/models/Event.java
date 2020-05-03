package com.dev.meetup.models;

import com.dev.meetup.enums.EventStatus;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "publisher_id")
    private User publisher;

    @ManyToMany
    @JoinTable(name = "user_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy="event")
    private Set<Comment> comments;

    private String title;
    private String description;
    private String markup;
    private String picture_path;
    private Date date;
    private Date upd = null;
    private EventStatus status;
    private String place;

    private int views;

    public Event() {}

    public Event(String title, String description, String markup, String picture_path, String date, User user, String place) throws ParseException {
        this.title = title;
        this.description = description;
        this.markup = markup;
        this.picture_path = picture_path;
        this.date =  new SimpleDateFormat("yyyy-MM-dd").parse(date);
        this.place = place;
        this.status = EventStatus.PREPARE;
        this.views = 0;
        this.publisher = user;
    }

    public void Update(String title, String description, String markup,
                       //String picture_path,
                       String date, String place) throws ParseException {
        this.title = title;
        this.description = description;
        this.markup = markup;
        //this.picture_path = picture_path;
        this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        this.place = place;
        this.upd = new Date();
    }

    public void addView() {
        this.views++;
    }

    public void addSubscribe(User subscriber) {
        users.add(subscriber);
    }

    public void deleteSubscribe(User subscriber) {
        users.remove(subscriber);
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMarkup() { return markup; }
    public void setMarkup(String markup) { this.markup = markup; }

    public String getPicture_path() { return picture_path; }
    public void setPicture_path(String picture_path) { this.picture_path = picture_path; }

    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }

    public User getPublisher() { return publisher; }
    public void setPublisher(User publisher) { this.publisher = publisher; }

    public Date getDate() { return date;}
    public void setDate(Date date) { this.date = date; }

    public String geStringDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }
    public String geInputDate() {
        return new SimpleDateFormat("yyy-MM-dd").format(date);
    }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public Date getUpd() { return upd; }
    public void setUpd(Date upd) { this.upd = upd; }

    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> subscribers) { this.users = subscribers; }

    public Set<Comment> getComments() { return comments; }
    public void setComments(Set<Comment> comments) { this.comments = comments; }

    public List<Comment> getOrderedComments() {
        ArrayList<Comment> results = new ArrayList<>(comments);
        Collections.reverse(results);
        
        return results;
    }
}


