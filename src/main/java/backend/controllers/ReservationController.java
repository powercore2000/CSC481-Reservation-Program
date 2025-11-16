package main.java.backend.controllers;

import main.java.backend.models.*;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;


@RestController

public class ReservationController {
	
	@GetMapping
	public String welcome() {
		
		return "Welcome to hell";
	}
			
    @PostMapping("/signUp")
    public String createUser(@RequestBody User user) {
        return "User created: " + user.getName();
    }

	@PostMapping("/login")
    public String login(@RequestBody String email, String password) {
		
        return "Hash pass and see if they exist!";
    }
	
	
    @GetMapping("/reservations")
    public ArrayList<Reservation> getAllReservations() {
        return new ArrayList<Reservation>();
    }
	@GetMapping("/users")
	public ArrayList<User> getAllUsers(){
		return new ArrayList<User>();
	}
}
