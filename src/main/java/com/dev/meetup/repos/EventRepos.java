package com.dev.meetup.repos;

import com.dev.meetup.models.Event;
import com.dev.meetup.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface EventRepos extends CrudRepository<Event, Long> {
    Boolean existsByTitle(String title);

    Event findByTitle(String title);
    Event findById(long id);
    List<Event> findAll();
    List<Event> findByTitleContainsIgnoreCase(String match);

    List<Event> findAllByPublisherOrderByIdDesc(User user);
    List<Event> getAllByDateAfterOrderByIdDesc(Date date);
    List<Event> getAllByDateBeforeOrderByIdDesc(Date date);
    List<Event> getAllByDateEqualsOrderById(Date date);
//    TODO Override save method to return id of inserted event
}
