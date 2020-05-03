package com.dev.meetup.repos;

import com.dev.meetup.models.Chat;
import com.dev.meetup.models.User;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepos extends CrudRepository<Chat, Long> {

    Chat findChatByFromAndTo( User from, User to);
}
