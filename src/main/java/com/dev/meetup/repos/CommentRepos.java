package com.dev.meetup.repos;

import com.dev.meetup.models.Comment;
import com.dev.meetup.models.Event;
import com.dev.meetup.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepos extends CrudRepository<Comment, Long>{

    Comment findCommentById (long id);

    List<Comment> findCommentsByEvent(Event event);
}


