package com.user.service;

import java.time.LocalDateTime;
import java.util.List;

import com.user.dtos.UserDTO;

public interface UserService 
{
	/**
     * Gets all users.
     *
     * @return the all users
     */
	List<UserDTO> getAllUsers();
	
	 /**
     * Find user by id user dto.
     *
     * @param id the id
     * @return the user dto
     */
	UserDTO findUserById(Long id);
	
	 /**
     * delete user by id 
     *
     * @param id the id
     * 
     */
	void deleteUserById(Long id);
	
	/**
     * delete user by id 
     *
     * @param first Name 
     * @param second Name
     * @param date of birthday
     */
	void createUser(String firstName,String lastName,LocalDateTime dob);
}
