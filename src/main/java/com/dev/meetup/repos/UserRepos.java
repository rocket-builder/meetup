package com.dev.meetup.repos;

import com.dev.meetup.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepos extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
