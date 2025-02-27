package com.snsc.spring_HotelMS.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snsc.spring_HotelMS.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);   // This method checks if a user exists in the database using their email.
	
	Optional<User> findByEmail(String email); // Retrieves a user by email (returns an Optional<User>).
}
