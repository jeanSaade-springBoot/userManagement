package com.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
	@Override
    Optional<User> findById(Long id);
	
	@Override
	void deleteById(Long id);	

}
