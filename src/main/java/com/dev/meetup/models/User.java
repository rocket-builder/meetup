package com.dev.meetup.models;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_event",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") }
    )
    private Set<Event> events = new HashSet<>();

    private String username, email, password, about, background_path, avatar_path;
    private Date signup_date;

    public User() {}
    public User(String login, String email, String password) {
        this.username = login;
        this.email = email;
        this.password = password;
        this.signup_date = new Date((new java.util.Date()).getTime());
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }

    public Date getSignup_date() {
        return signup_date;
    }
    public void setSignup_date(Date signup_date) {
        this.signup_date = signup_date;
    }

    public Set<Event> getEvents() { return events; }
    public void setEvents(Set<Event> events) { this.events = events; }

    public String getStringDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(signup_date);
    }

    public String getBackground_path() { return background_path; }
    public void setBackground_path(String background_path) { this.background_path = background_path; }

    public String getAvatar_path() { return avatar_path; }
    public void setAvatar_path(String avatar_path) { this.avatar_path = avatar_path; }
}
