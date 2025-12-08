package backend.controllers;

import database.dto.*;
import database.queries.ReservationQueries;
import database.queries.RestaurantQueries;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.models.ReservationModel;
import backend.models.RestaurantModel;
import backend.services.ReservationMapper;
import backend.services.RestaurantMapper;


@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	private static ReservationDTO cachedReservation;
	public static ReservationDTO getCachedReservation(){return cachedReservation;}
	public static void setCachedReservation(ReservationDTO dto) {cachedReservation = dto;}
	
	
	
    @GetMapping("/currentsReservations")
    public static ArrayList<ReservationDTO> currentUserReservations() {
    	
    	List<ReservationModel> list = ReservationQueries.getAllReservationsForUser(UserController.getCurrentUser().getUserId());
    	
    	ArrayList<ReservationDTO> allReservations = new ArrayList<ReservationDTO>();
        
        
        for (ReservationModel model : list) {
     	   
        	allReservations.add(ReservationMapper.toDTO(model));
     	   
        }
        
        return allReservations;
             
    }
    
    
    
    @PostMapping("/getUserReservations")
    public static ArrayList<ReservationDTO> getAllReservationsForUser(@RequestBody UserDTO user) {
    	
    	List<ReservationModel> list = ReservationQueries.getAllReservationsForUser(user.getUserId());
    	
    	ArrayList<ReservationDTO> allReservations = new ArrayList<ReservationDTO>();
        
        for (ReservationModel model : list) {
     	   
        	allReservations.add(ReservationMapper.toDTO(model));
     	   
        }
        
        return allReservations;
             
    }

    @PostMapping("/create")
    public static ResponseEntity<Boolean> createReservation(@RequestBody ReservationDTO reservationDto) {

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
		boolean success = ReservationQueries.createReservationWithFood(reservation, reservation.getFoodSelections());
        database.queries.ReservationQueries.listAll();		 	
        return true;
		
	}
	
}
