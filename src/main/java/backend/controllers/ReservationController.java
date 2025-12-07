package backend.controllers;

import database.dto.*;


import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;


@RestController

public class ReservationController {
	
	@GetMapping
	public static String welcome() {
		
		return "Welcome to hell";
	}
			
    @PostMapping("/signUp")
    public static Boolean createUser(@RequestBody UserDTO newUser) {
        System.out.println( "User created: " + newUser.getName());
        database.queries.UserQueries.insert(newUser);
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
    public static Boolean getAllUsers() {
    	database.queries.UserQueries.findAll();
        return true;
    }

	@PostMapping("/login")
    public static String loginUser(@RequestBody UserDTO loginUser) {
		
        return "Logging in " + loginUser.getEmail();
    }
	
	
    @GetMapping("/reservations")
    public static ArrayList<ReservationDTO> getAllReservations() {
        return new ArrayList<ReservationDTO>();
    }

	
	public static void CreateReservation(ReservationDTO reservation) {
		
		System.out.println("Created reservation: " + reservation);
		//ReservationModel resMod = ReservationMapper.toModel(reservation);     
        //database.queries.ReservationQueries.createReservation(reservation);
		
        database.queries.ReservationQueries.saveReservation(reservation);
        database.queries.ReservationQueries.listAll();		 	

		
	}
	
}
