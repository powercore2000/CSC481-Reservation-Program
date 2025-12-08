package backend.controllers;

import database.dto.*;


import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.models.ReservationMapper;
import backend.models.ReservationModel;


@RestController

public class ReservationController {
	
	
    @GetMapping("/reservations")
    public static ArrayList<ReservationDTO> getAllReservations() {
        return new ArrayList<ReservationDTO>();
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createReservation(@RequestBody ReservationDTO reservationDto) {

        Boolean result = CreateReservation(reservationDto);

        if (result) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }
    
	public static Boolean CreateReservation(ReservationDTO reservation) {
	    if(!UserController.isUserLoggedIn()) {
	    	System.out.println("Cant add reservation user not loggedin! Logging in default user:");
	    	UserController.loginUser(new UserDTO("Debug Userman", "bobBot69@hotbotmail.com", "310 111 1234", "debugBotPAs$12"));
	    	//return false;
	    }
	    
		reservation.setEmail(UserController.getCurrentUser().getEmail()); 
		System.out.println("Created reservation: " + reservation);  
        database.queries.ReservationQueries.createReservation(reservation);
        database.queries.ReservationQueries.listAll();		 	
        return true;
		
	}
	
}
