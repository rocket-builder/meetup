package com.dev.meetup.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "publisher_id")
    private User publisher;

    private String title;
    private String description;
    private String markup;
    private String picture_path;

    private String dates;

    private int views;

    public Event() {}

    public Event(String title, String description, String markup, String picture_path, String dates, User user) {
        this.title = title;
        this.description = description;
        this.markup = markup;
        this.picture_path = picture_path;
        this.dates = dates;
        this.views = 0;
        this.publisher = user;
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

    public String getDates() { return dates;}
    public void setDates(String dates) { this.dates = dates; }
}
