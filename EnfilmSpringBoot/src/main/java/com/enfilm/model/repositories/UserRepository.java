package com.enfilm.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.enfilm.model.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	public User findByEmail(String email);
	public User findByEmailAndPassword(String email, String password);
}
