package backend.controllers;

import database.dto.*;


import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;


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
    
    @GetMapping("/signUp2")
    public static Boolean createUser() {
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
		
		Boolean validUser = !database.queries.UserQueries.findByEmail(loginUser.getEmail()).isEmpty();
		if(validUser){
			currentUser = loginUser;
			return true;
		}
		
		System.out.println("Invalid user passed in for login!");
        return false;
    }
	
	

	
}
