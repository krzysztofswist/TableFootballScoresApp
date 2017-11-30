package com.score.repo;

import org.springframework.data.repository.CrudRepository;

import com.score.model.User;

public interface UserRepository extends CrudRepository<User, String> {

}
