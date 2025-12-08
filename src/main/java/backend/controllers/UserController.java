package backend.controllers;

import database.dto.*;


import java.util.ArrayList;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import backend.models.UserModel;
import backend.services.UserMapper;


@RestController
@RequestMapping("/users")
public class UserController {
	
	private static UserDTO currentUser;
	public static UserDTO getCurrentUser() {return currentUser;}
	
	
	public static Boolean isUserLoggedIn() {return currentUser != null;}
	
	@GetMapping
	public static String welcome() {
		
		return "Welcome to hell";
	}
			
    @PostMapping("/signUp")
    public static Boolean createUser(@RequestBody UserDTO newUser) {
    	
        System.out.println( "User created: " + newUser.getName());
        long key = database.queries.UserQueries.insert(newUser);
        if(key == -1L) {
        	System.out.println("User not made");
        	//throw new RuntimeException("hu?\ntrace-line1\ntrace-line2");
        	return false;
        }
        currentUser = newUser;
        database.queries.UserQueries.findAll();
        return true;
    }
    
    @GetMapping("/debugSignUp")
    public static Boolean createUserDebug() {
    	UserDTO newUser = new UserDTO("Bobby2", "bob2@gmail.com", "310 111 1234", "4566667");
        System.out.println( "User created: " + newUser.getName());
        database.queries.UserQueries.insert(newUser);
        database.queries.UserQueries.findAll();
        return true;
    }
    
    @GetMapping("/listAllUsers")
    public static void getAllUsers() {
    	database.queries.UserQueries.findAll();

    }

	@PostMapping("/login")
    public static Boolean loginUser(@RequestBody UserDTO loginUser) {
		
		System.out.printf("Signing in with email:%s pass:%s%n",loginUser.getEmail(), loginUser.getPasswordString());
		Optional<UserModel> validUser = database.queries.UserQueries.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPasswordString());
		if(validUser.isEmpty()) {
			System.out.println("Invalid user passed in for login!");
	        return false;
		}
			
			currentUser = UserMapper.toDTO(validUser.get());
			System.out.println("Logged in user " + loginUser.getEmail());
			return true;
		}


	
}
