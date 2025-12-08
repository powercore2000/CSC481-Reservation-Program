package backend.controllers;

import database.dto.*;
import database.queries.FoodQueries;
import database.queries.ReservationQueries;
import database.queries.RestaurantQueries;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.models.FoodModel;
import backend.models.ReservationModel;
import backend.models.RestaurantModel;
import backend.services.FoodMapper;
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
    
    @PostMapping("/addFoodCurrentReservation")
    public static ResponseEntity<Boolean> addFoodToCurrentReservation(@RequestBody FoodDTO food) {
    	
    	ReservationModel currentResModel = ReservationMapper.toModel(cachedReservation);
    	Boolean success = FoodQueries.attachToReservation(currentResModel.getLongId(), food.getId());
        
        if(success)
        	return ResponseEntity.ok(true);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
             
    }
    
    @PostMapping("/addFoodTargetReservation")
    public static ResponseEntity<Boolean> addFoodToTargetReservation(@RequestBody FoodDTO food, @RequestBody ReservationDTO reservation) {
    	
    	ReservationModel targetResModel = ReservationMapper.toModel(reservation);
    	Boolean success = FoodQueries.attachToReservation(targetResModel.getLongId(), food.getId());
        
        if(success)
        	return ResponseEntity.ok(true);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
             
    }
    
    @GetMapping("/getFoodCurrentReservations")
    public static ArrayList<FoodDTO> getAllFoodFromReservation() {
    	
    	List<FoodModel> list = null;
    	
    	ArrayList<FoodDTO> allFood = new ArrayList<FoodDTO>();
        
        for (FoodModel food : list) {
     	   
        	allFood.add(FoodMapper.toDTO(food));
     	   
        }
        
        return allFood;
             
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
		cachedReservation = database.queries.ReservationQueries.createReservation(reservation).get();
        database.queries.ReservationQueries.listAll();		 	
        return true;
		
	}
	
}
