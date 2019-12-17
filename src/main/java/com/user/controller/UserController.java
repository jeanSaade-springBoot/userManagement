package com.user.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dtos.UserDTO;
import com.user.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "users")
public class UserController {
	
	 private UserService userService;

	    public UserController(UserService userService) 
	    {
	        this.userService = userService;
	    }

	    /**
	     * Rest Resource that get the list of All users
	     *
	     * @return list of all {}
	     */
	    @ApiOperation(
	            notes = "Returns  list of all stored users.",
	            value = "Get a list of all stored users.",
	            nickname = "listAll",
	            tags = {"users"})
	    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<List<UserDTO>> getAllUsers() 
	    {
	        return new ResponseEntity<>(this.userService.getAllUsers(),HttpStatus.OK);
	    }
	    
	    
	    /**
	     * Rest Resource that return single Customer by Id
	     *
	     * @param customerId
	     * @return customer
	     */
	    @ApiOperation(notes = "Returns a user.",
	            value = "Get user",
	            nickname = "getById",
	            tags = {"users"})
	    @GetMapping(value = "id/{user-id}")
	    public ResponseEntity<UserDTO> getUserById(@PathVariable("user-id") Long userId) {
 
	        UserDTO userDTO = this.userService.findUserById(userId);
	        log.info("GET user with id {}: {}", userId, userDTO);
	        return new ResponseEntity<>(userDTO, HttpStatus.OK);
	    }
	    
	    /**
	     * Rest Resource that save customer
	     *
	     * @param first name
	     * @param last name
	     * @return customer
	     */
	    @ApiOperation(notes = "Returns success.",
	                  value = "save Customer",
	                  nickname = "getById",
	                  tags = {"users"})
	    @GetMapping(value = "SaveCustomer/{first-name}/{last-name}")
	    public String SaveCustomer(@PathVariable("first-name") String firstName,
	                               @PathVariable("last-name") String lastName
	                               /*,
	                               @PathVariable("date-of-birthday") String dob*/) {
	    	LocalDateTime dob= LocalDateTime.now();
	        this.userService.createUser(firstName, lastName, dob);
	        log.info("Customer save firstName {}", firstName);
	        log.info("Customer save lastName {}", lastName);
	        log.info("Customer save dob {}", dob);
	        
	        return "saved";
	    }
	    
	    /**
	     * Rest Resource that save customer
	     *
	     * @param first name
	     * @param last name
	     * @return customer
	     */
	    @ApiOperation(notes = "Returns success.",
	                  value = "delete user",
	                  nickname = "deleteUserById",
	                  tags = {"users"})
	    @GetMapping(value = "deleteUserById/{user-id}")
	    public String deleteUserById(@PathVariable("user-id") Long id) {
	        this.userService.deleteUserById(id);
	        log.info("delete user with id {}", id);
	        return "deleted";
	    }


}
