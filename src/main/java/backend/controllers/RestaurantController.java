package backend.controllers;

import database.dto.*;
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
@RequestMapping("/restaurants")
public class RestaurantController {
	
    // Handles which restaurant the browsing user has selected to make a reservation at
	private static long selectedRestaurantId = 1;
	
	public static long getSelectedRestaurantID() { return selectedRestaurantId;}
	
	public static void setSelectedRestaurantID(long id) {selectedRestaurantId = id;}
	
    @GetMapping("/listAll")
    public static List<RestaurantDTO> getAllRestaurants() {
    	List<RestaurantDTO> allRestaurants = new ArrayList<RestaurantDTO>();
       
       List<RestaurantModel> allModels = RestaurantQueries.findAll();
       
       for (RestaurantModel r : allModels) {
    	   
    	   allRestaurants.add(RestaurantMapper.toDTO(r));
       }
       
       return allRestaurants;
    }
    

    @PostMapping("/updateSchedule")
    public ResponseEntity<Boolean> createReservation(@RequestBody RestaurantDTO restaurantDto) {

            return ResponseEntity.ok(true);

    }
    
	
}
