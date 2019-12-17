package com.user.service.impl;

import static java.lang.String.format;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import com.user.domain.User;
import com.user.dtos.UserDTO;
import com.user.repository.UserRepository;
import com.user.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserServiceImpl implements UserService{

	 private static final String USER_DOES_NOT_EXIST = "User with id %s does not exist.";
	 private UserRepository userRepository;
	 
	 /**
     * Instantiates a new User service.
     *
     * @param userRepository the user repository
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
	@Override
	public List<UserDTO> getAllUsers() {
		    log.info("Getting all users");
	        return this.userRepository.findAll()
	                .stream()
	                .map(user -> UserDTO.builder()
	                .id(user.getId())
	                .firstName(user.getFirstName())
	                .lastName(user.getLastName())
	                .dateOfBirth(user.getDateOfBirth()).build())
	                .collect(Collectors.toList());
	}

	@Override
	public UserDTO findUserById(Long id) {
		// TODO Auto-generated method stub
		log.info("Find customer by id {}", id);

        User user = this.findById(id);
        return  UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth()).build();
	}

	@Override
	public void deleteUserById(Long id) {
		this.userRepository.delete(findById(id));
	}

	@Override
	public void createUser(String firstName, String lastName, LocalDateTime dateOfBirth) {
		
		 User user = User.builder()
	                .firstName(firstName)
	                .lastName(lastName)
	                .dateOfBirth(dateOfBirth)
	                .build();
	     user = userRepository.save(user);
	}
	
	 /**
     * Find customer by id customer.
     *
     * @param id the id
     * @return the customer
     */
    private User findById (Long id){
        return  this.userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(format(USER_DOES_NOT_EXIST, id)));
    }
    
    
    
    

}
