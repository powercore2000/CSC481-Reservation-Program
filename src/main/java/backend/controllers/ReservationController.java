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
    public static String createUser(@RequestBody UserDTO newUser) {
        return "User created: " + newUser.getName();
    }

	@PostMapping("/login")
    public static String login(@RequestBody UserDTO loginUser) {
		
        return "Logging in " + loginUser.getEmail();
    }
	
	
    @GetMapping("/reservations")
    public static ArrayList<ReservationDTO> getAllReservations() {
        return new ArrayList<ReservationDTO>();
    }
	@GetMapping("/users")
	public static ArrayList<UserDTO> getAllUsers(){
		return new ArrayList<UserDTO>();
	}
	
	public static void CreateReservation(ReservationDTO reservation) {
		
		System.out.println("Created reservation: " + reservation);
		//ReservationModel resMod = ReservationMapper.toModel(reservation);     
        //database.queries.ReservationQueries.createReservation(reservation);
		
        database.queries.ReservationQueries.saveReservation(reservation);
        database.queries.ReservationQueries.listAll();		 	

		
	}
	
}
