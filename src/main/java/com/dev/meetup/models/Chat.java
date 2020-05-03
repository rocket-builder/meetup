package com.dev.meetup.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy="message")
    private Set<Message> messages = new HashSet<>();

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "from_id")
    private User from;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "to_id")
    private User to;

    public Chat(User from, User to) {
        this.from = from;
        this.to = to;
    }

    public User getFrom() { return from; }
    public User getTo() { return to; }

    public Set<Message> getMessages() { return messages; }

    public long getId() { return id; }
}
