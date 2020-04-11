package com.dev.meetup.repos;

import com.dev.meetup.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepos extends CrudRepository<Event, Long> {
    Event findByTitle(String title);
    Event findById(int id);

//    TODO Override save method to return id of inserted event
}
