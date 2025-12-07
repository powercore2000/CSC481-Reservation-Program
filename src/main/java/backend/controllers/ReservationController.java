package backend.controllers;

import backend.models.*;
import database.dto.ReservationDTO;

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
    public ArrayList<ReservationDTO> getAllReservations() {
        return new ArrayList<ReservationDTO>();
    }
	@GetMapping("/users")
	public ArrayList<User> getAllUsers(){
		return new ArrayList<User>();
	}
	
	public static void CreateReservation(ReservationDTO reservation) {
		
		System.out.println("Created reservation: " + reservation);
		//ReservationModel resMod = ReservationMapper.toModel(reservation);     
        //database.queries.ReservationQueries.createReservation(reservation);
		
        database.queries.ReservationQueries.saveReservation(reservation);
        database.queries.ReservationQueries.listAll();		 	

		
	}
	
}
