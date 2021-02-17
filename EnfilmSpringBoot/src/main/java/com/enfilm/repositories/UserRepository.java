package com.enfilm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
