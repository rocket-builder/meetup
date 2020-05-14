package com.dev.meetup.repos;

import com.dev.meetup.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepos extends CrudRepository<User, Long> {

    List<User> findAll();
    List<User> findByUsernameContainsIgnoreCase(String username);
    User findByUsername(String username);
    User findById (long id);

    void deleteByUsername(String username);
}
